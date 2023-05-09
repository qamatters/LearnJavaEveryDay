package org.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class imageCompareResult {
    private static int[][] matrix;
    private static Integer minimalRectangleSize = 1;
    private static double differenceConstant;
    private static double pixelToleranceLevel = 0.1;
    private static double percentOpacityDifferenceRectangles = 20.0;
    private static int counter = 2;
    private static int threshold = 5;
    private static int regionCount = counter;

    public static void main(String[] args) throws IOException {
        String result = "diff_" + System.currentTimeMillis();
        File actual = new File("src/main/resources/Images/actual.png");
        File expected = new File("src/main/resources/Images/expected.png");
        compareImages(actual, expected, result);
    }

    private static void compareImages(File actual, File expected, String resultOfComparison) throws IOException {
        BufferedImage bImage = ImageIO.read(actual);
        BufferedImage cImage = ImageIO.read(expected);
        int height = bImage.getHeight();
        int width = bImage.getWidth();
        int highlight = Color.MAGENTA.getRGB();
        BufferedImage rImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = rImage.createGraphics();
        g2d.setStroke(new BasicStroke(3));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    int pixelC = cImage.getRGB(x, y);
                    int pixelB = bImage.getRGB(x, y);
                    if (pixelB == pixelC) {
                        rImage.setRGB(x, y, bImage.getRGB(x, y));
                    } else {
                        List<Rectangle> rectangles = populateRectangles(bImage, cImage);
                        rImage = drawRectangles(rectangles, bImage);
//                        rImage.setRGB(x, y, highlight);
                    }
                } catch (Exception e) {
                    rImage.setRGB(x, y, 0x80ff0000);
                }
            }
        }
        String filePath = actual.toPath().toString();
        String fileExtension = filePath.substring(filePath.lastIndexOf('.'), filePath.length());
        if (fileExtension.toUpperCase().contains("PNG")) {
            createPngImage(rImage, resultOfComparison + fileExtension);
        } else {
            createJpgImage(rImage, resultOfComparison + fileExtension);
        }
    }

    private static BufferedImage drawRectangles(List<Rectangle> rectangles, BufferedImage actual) {

        BufferedImage resultImage = deepCopy(actual);
        Graphics2D graphics = preparedGraphics2D(resultImage);
        drawRectanglesOfDifferences(rectangles, graphics);
        return resultImage;
    }

    private static void drawRectanglesOfDifferences(List<Rectangle> rectangles, Graphics2D graphics) {
        List<Rectangle> rectanglesForDraw;
        graphics.setColor(Color.MAGENTA);
        int maximalRectangleCount = -1;
        rectanglesForDraw = rectangles.stream()
                .sorted(Comparator.comparing(Rectangle::size))
                .skip(rectangles.size() - maximalRectangleCount)
                .collect(Collectors.toList());
        draw(graphics, rectanglesForDraw);
        fillRectangles(graphics, rectanglesForDraw, percentOpacityDifferenceRectangles);
    }

    private static void fillRectangles(Graphics2D graphics, List<Rectangle> rectangles, double percentOpacity) {

        graphics.setColor(new Color(graphics.getColor().getRed(),
                graphics.getColor().getGreen(),
                graphics.getColor().getBlue(),
                (int) (percentOpacity / 100 * 255)
        ));
        rectangles.forEach(rectangle -> graphics.fillRect(
                rectangle.getMinPoint().x - 1,
                rectangle.getMinPoint().y - 1,
                rectangle.getWidth() - 2,
                rectangle.getHeight() - 2)
        );
    }

    private static void draw(Graphics2D graphics, List<Rectangle> rectangles) {
        rectangles.forEach(rectangle -> graphics.drawRect(
                rectangle.getMinPoint().x,
                rectangle.getMinPoint().y,
                rectangle.getWidth() - 1,
                rectangle.getHeight() - 1)
        );
    }

    private static Graphics2D preparedGraphics2D(BufferedImage resultImage) {
        Graphics2D graphics = resultImage.createGraphics();
        graphics.setStroke(new BasicStroke(1));
        return graphics;
    }


    public static BufferedImage deepCopy(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


    public static void createPngImage(BufferedImage image, String fileName) throws IOException {
        ImageIO.write(image, "png", new File(fileName));
    }

    public static void createJpgImage(BufferedImage image, String fileName) throws IOException {
        ImageIO.write(image, "jpg", new File(fileName));
    }

    private static List<Rectangle> populateRectangles(BufferedImage bImage, BufferedImage cImage) {
        long countOfDifferentPixels = populateTheMatrixOfTheDifferences(bImage, cImage);

        if (countOfDifferentPixels == 0) {
            return emptyList();
        }
        groupRegions();
        List<Rectangle> rectangles = new ArrayList<>();
        while (counter <= regionCount) {
            Rectangle rectangle = createRectangle();
            if (!rectangle.equals(Rectangle.createDefault()) && rectangle.size() >= minimalRectangleSize) {
                rectangles.add(rectangle);
            }
            counter++;
        }
        return mergeRectangles(mergeRectangles(rectangles));
    }

    private static List<Rectangle> mergeRectangles(List<Rectangle> rectangles) {
        int position = 0;
        while (position < rectangles.size()) {
            if (rectangles.get(position).equals(Rectangle.createZero())) {
                position++;
            }
            for (int i = 1 + position; i < rectangles.size(); i++) {
                Rectangle r1 = rectangles.get(position);
                Rectangle r2 = rectangles.get(i);
                if (r2.equals(Rectangle.createZero())) {
                    continue;
                }
                if (r1.isOverlapping(r2)) {
                    rectangles.set(position, r1.merge(r2));
                    r2.makeZeroRectangle();
                    if (position != 0) {
                        position--;
                    }
                }
            }
            position++;
        }

        return rectangles.stream().filter(it -> !it.equals(Rectangle.createZero())).collect(Collectors.toList());
    }

    private static Rectangle createRectangle() {
        Rectangle rectangle = Rectangle.createDefault();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == counter) {
                    updateRectangleCreation(rectangle, x, y);
                }
            }
        }
        return rectangle;
    }

    private static void updateRectangleCreation(Rectangle rectangle, int x, int y) {
        if (x < rectangle.getMinPoint().getX()) {
            rectangle.getMinPoint().x = x;
        }
        if (x > rectangle.getMaxPoint().getX()) {
            rectangle.getMaxPoint().x = x;
        }

        if (y < rectangle.getMinPoint().getY()) {
            rectangle.getMinPoint().y = y;
        }
        if (y > rectangle.getMaxPoint().getY()) {
            rectangle.getMaxPoint().y = y;
        }
    }

    private static void groupRegions() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 1) {
                    joinToRegion(x, y);
                    regionCount++;
                }
            }
        }
    }

    private static void joinToRegion(int x, int y) {
        matrix[y][x] = regionCount;
        for (int i = 0; i < threshold; i++) {
            joinToRegion(x + 1 + i, y);
            joinToRegion(x, y + 1 + i);

            joinToRegion(x + 1 + i, y - 1 - i);
            joinToRegion(x - 1 - i, y + 1 + i);
            joinToRegion(x + 1 + i, y + 1 + i);
        }
    }

    private static long populateTheMatrixOfTheDifferences(BufferedImage expected, BufferedImage actual) {
        long countOfDifferentPixels = 0;
        matrix = new int[expected.getHeight()][expected.getWidth()];
        for (int y = 0; y < expected.getHeight(); y++) {
            for (int x = 0; x < expected.getWidth(); x++) {
                if (isDifferentPixels(expected.getRGB(x, y), actual.getRGB(x, y))) {
                    matrix[y][x] = 1;
                    countOfDifferentPixels++;
                }
            }
        }
        return countOfDifferentPixels;
    }

    private static boolean isDifferentPixels(int expectedRgb, int actualRgb) {
        if (expectedRgb == actualRgb) {
            return false;
        } else if (pixelToleranceLevel == 0.0) {
            return true;
        }
        int red1 = (expectedRgb >> 16) & 0xff;
        int green1 = (expectedRgb >> 8) & 0xff;
        int blue1 = (expectedRgb) & 0xff;
        int red2 = (actualRgb >> 16) & 0xff;
        int green2 = (actualRgb >> 8) & 0xff;
        int blue2 = (actualRgb) & 0xff;

        return (Math.pow(red2 - red1, 2) + Math.pow(green2 - green1, 2) + Math.pow(blue2 - blue1, 2))
                > differenceConstant;
    }
}

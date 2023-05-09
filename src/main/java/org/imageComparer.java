package org;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class imageComparer {
    public static void main(String[] args) throws IOException {
        String result = "diff_" + System.currentTimeMillis();
        File baseFile = new File("src/main/resources/Images/actual.png");
        File compareImage = new File("src/main/resources/Images/expected.png");
        compareWithBaseImage(baseFile, compareImage, result);
    }

    public static void compareWithBaseImage(File baseImage, File CompareImage, String resultOfComparison) throws IOException {
        BufferedImage bImage = ImageIO.read(baseImage);
        BufferedImage cImage = ImageIO.read(CompareImage);
        int height = bImage.getHeight();
        int width = bImage.getWidth();

        Color c=new Color(152,251,152, 100);

        int highlight = c.getRGB();
        BufferedImage rImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    int pixelC = cImage.getRGB(x, y);
                    int pixelB = bImage.getRGB(x, y);
                    if (pixelB == pixelC) {
                        rImage.setRGB(x, y, bImage.getRGB(x, y));
                    } else {
                        rImage.setRGB(x, y, highlight);
                    }
                } catch (Exception e) {
                    rImage.setRGB(x, y, 0x80ff0000);
                }
            }
        }
        String filePath = baseImage.toPath().toString();
        String fileExtension = filePath.substring(filePath.lastIndexOf('.'), filePath.length());
        if (fileExtension.toUpperCase().contains("PNG")) {
            createPngImage(rImage, resultOfComparison + fileExtension);
        } else {
            createJpgImage(rImage, resultOfComparison + fileExtension);
        }
    }

    public static void createPngImage(BufferedImage image, String fileName) throws IOException {
        ImageIO.write(image, "png", new File(fileName));
    }

    public static void createJpgImage(BufferedImage image, String fileName) throws IOException {
        ImageIO.write(image, "jpg", new File(fileName));
    }



}

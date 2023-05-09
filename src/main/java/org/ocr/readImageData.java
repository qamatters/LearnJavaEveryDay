package org.ocr;

import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class readImageData {
    public static void main(String[] args) throws TesseractException, IOException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/traindata/");
        tesseract.setLanguage("eng");
        File file = new File("src/main/resources/data/IMG-7910.PNG");
        File output =  new File("src/main/resources/data/output.PNG");
        BufferedImage image = ImageIO.read(file);
        List<Word> text = tesseract.getWords(image, 1);
        System.out.println("Text is : " + text);

//        highlightWord(image);
//        try {
//            ImageIO.write(image, "png",output );
//        } catch (Exception e) {
//            System.out.println("[ERROR] Could not save image.");
//        }
//        try {
//            String result = tesseract.doOCR(image);
//            System.out.println(result);
//        } catch (TesseractException e) {
//            System.err.println(e.getMessage());
//        }
    }

    public static void highlightWord(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.magenta);
        g2d.setStroke(new BasicStroke(3));
        g2d.fillRect(26, 72, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.dispose();
    }

}

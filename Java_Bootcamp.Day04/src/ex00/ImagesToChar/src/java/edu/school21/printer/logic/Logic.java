package edu.school21.printer.logic;

import edu.school21.printer.app.Program;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Logic {
    private final char white;
    private final char black;
    private final BufferedImage image;

    public Logic(char white, char black, String imagePath) throws IOException {
        this.white = white;
        this.black = black;
        this.image = ImageIO.read(new File(imagePath));
    }

    public void print() {
        int width = image.getWidth();
        int heigth = image.getHeight();

        for (int y = 0; y < heigth; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                if (pixel == Color.WHITE.getRGB()) {
                    System.out.print(white);
                } else if (pixel == Color.BLACK.getRGB()) {
                    System.out.print(black);
                }
            }
            System.out.println();
        }
    }
}

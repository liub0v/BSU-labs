import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.util.Random;

class Pieces {

    private int[] puzzles;
    private boolean gameOver;
    private Image[] image_puzzle;
    private BufferedImage image;
    private int size;
    private int wight, height;

    Pieces(int size) {
        this.size = size;
        puzzles = new int[size * size];
        newGame();
        wight = 1200;
        height = 700;
    }

    void newGame() {
        try {
            int n = (int) (3.0 * Math.random());
            image_puzzle = new Image[size * size];
            image = ImageIO.read(new File("img" + (n + 1) + ".jpg"));
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            int counter = 0;


            for (int i = 0; i < imageWidth; i = i + imageWidth / size) {
                for (int j = 0; j < imageHeight; j = j + imageHeight / size) {

                    int[] picture = new int[imageWidth * imageHeight];

                    PixelGrabber grabber = new PixelGrabber(image, i + 1, j + 2,
                            i + imageWidth / size,
                            j + imageHeight / size, picture,
                            0, imageWidth / size
                    );
                    grabber.grabPixels();

                    image_puzzle[counter] = Toolkit.getDefaultToolkit().createImage(
                            new MemoryImageSource(imageWidth / size,
                                    imageHeight / size, picture,
                                    0, imageWidth / size));
                    ++counter;
                }
            }

            for (int i = 0; i < puzzles.length; i++) {
                puzzles[i] = i;
            }
            //миксую части картинки
            Random rnd = new Random();
            for (int i = 1; i < puzzles.length; i++) {
                int j = rnd.nextInt(i);
                int temp = puzzles[i];
                puzzles[i] = puzzles[j];
                puzzles[j] = temp;
            }

            gameOver = false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    boolean isSolved() {
        for (int i = puzzles.length - 1; i >= 0; i--) {
            if (puzzles[i] != i)
                return false;
        }
        return true;
    }


    void swap(int i, int j) {
        int temp = puzzles[i];
        puzzles[i] = puzzles[j];
        puzzles[j] = temp;
    }

    int getIndex(int i, int j) {
        return size * (i / (wight / size)) + (j / ((height - 40) / size));
    }

    void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    boolean isGameOver() {
        return gameOver;
    }

    Image getImage() {
        return image;
    }

    Image getImage(int i) {
        return image_puzzle[i];
    }

    int getPuzzles(int i) {
        return puzzles[i];
    }

    int getSize() {
        return size;
    }

    int getHeight() {
        return height;
    }

    int getWight() {
        return wight;
    }
}

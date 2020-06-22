import javax.swing.*;
import java.awt.*;


public class Prompt extends JFrame {
    private Image image;

    Prompt(Image img) {
        super();
        image = img;
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, 800, 600, this);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;


public class Clock extends JPanel {
    private int size;
    private int second;
    private int angle;
    private int radius;

    Clock() {
        setBackground(Color.BLACK);
        size = 400;
        setPreferredSize(new Dimension(size, size));
        second = 0;
        angle = 0;
        radius = 10 + size / 2;
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                repaint();
                if (second >= 1) {
                    second = 0;
                    angle += 6;
                }
                if (angle >= 360) angle = 0;
                second++;
            }
        });
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.ORANGE);
        g2.setStroke(new BasicStroke(2));
        Point2D center = new Point2D.Double(radius, radius);
        Point2D end = new Point2D.Double(radius - (int) ((size / 2) * Math.sin(-Math.toRadians(angle))),
                radius - (int) ((size / 2) * Math.cos(-Math.toRadians(angle))));
        g2.draw(new Line2D.Double(center, end));
        g2.setStroke(new BasicStroke(6));
        g2.draw(new Ellipse2D.Double(10, 10, size, size));

    }

}

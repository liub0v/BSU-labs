import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyJFrame extends JFrame {

    public static void main(String[] args) {
        new MyJFrame();
    }

    private MyJFrame() {

        super("Clock");
        setPreferredSize(new Dimension(440, 460));
        this.setLayout(new BorderLayout());
        this.add(new Clock(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }
}
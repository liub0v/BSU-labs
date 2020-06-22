import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame {
    public static void main(String[] args) {
        JFrame frame=new JFrame("Mini Word Art");
        frame.setSize(400, 70);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TextField text = new TextField(15);
        JPanel panel = new JPanel();
        panel.add(text);

        JButton stdDraw3D_text = new JButton("view 1");
        JButton textRotation=new JButton("view 2");
        panel.add(textRotation);
        panel.add(stdDraw3D_text);
        stdDraw3D_text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MyFrame myFrame=new MyFrame(text.getText());
            }
        });
        textRotation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StdDraw3D.setScale(-0.05, 0.3);
                StdDraw3D.text3D(0, 0, 0, text.getText());
                StdDraw3D.show();
            }
        });

        frame.add(panel);
        frame.setVisible(true);

    }
}

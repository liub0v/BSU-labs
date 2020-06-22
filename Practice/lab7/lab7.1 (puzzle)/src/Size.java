import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Size extends JFrame implements ActionListener {

    private JTextField sizeField;

    private Size() {

        super("Enter size");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sizeField = new JTextField(5);
        JPanel panel = new JPanel();
        panel.add(sizeField);
        sizeField.addActionListener(this);
        JButton ok = new JButton("OK");
        ok.addActionListener(this);
        ok.setActionCommand("ok");
        panel.add(ok);
        add(panel);
        setSize(300, 70);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) throws NumberFormatException {
        String cmd = actionEvent.getActionCommand();
        try {
            if (cmd.equals("ok")) {
                dispose();
                String sizeOfgame = sizeField.getText();
                new MyJFrame(Integer.parseInt(sizeOfgame));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter number");

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Size().setVisible(true);
            }

        });

    }
}

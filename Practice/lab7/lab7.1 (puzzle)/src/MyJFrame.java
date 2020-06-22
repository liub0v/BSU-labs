import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyJFrame extends JFrame {

    private MyJPanel myJPanel;
    private Pieces pieces;


    MyJFrame(int size) {
        super("Puzzle");

        JButton newGame = new JButton("NEW");
        JButton showImage = new JButton("FULL");
        JPanel buttonPanel = new JPanel();
        pieces = new Pieces(size);
        myJPanel = new MyJPanel(pieces);
        myJPanel.setBackground(Color.LIGHT_GRAY);
        JPanel mainPanel = new JPanel(new BorderLayout());

        buttonPanel.add(newGame);
        buttonPanel.add(showImage);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(myJPanel, BorderLayout.CENTER);
        add(mainPanel);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getContentPane(), "Do you want to start a new game?\n Current game will be reset", "", JOptionPane.OK_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {
                    pieces.newGame();
                    myJPanel.setGame(pieces);
                    repaint();


                }
            }
        });

        showImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prompt img = new Prompt(pieces.getImage());

            }
        });


        setSize(pieces.getWight(), pieces.getHeight());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }


}


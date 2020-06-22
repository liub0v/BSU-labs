import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyJPanel extends JPanel {

    private int x1, y1, x2, y2;
    private int size;
    private Pieces game;
    private int wight, height;

    MyJPanel(Pieces g) {

        super();
        setGame(g);
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        this.size = g.getSize();
        wight = g.getWight();
        height = g.getHeight() - 40;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!game.isGameOver()) {
                    int x = e.getX();
                    int y = e.getY();

                    if (x < 0 || x > wight || y < 30 || y > height) return;
                    if (x1 == 0 && y1 == 0) {
                        x1 = x;
                        y1 = y;
                    } else {
                        x2 = x;
                        y2 = y;
                        int i = game.getIndex(x1, y1);
                        int j = game.getIndex(x2, y2);
                        game.swap(i, j);
                        x1 = y1 = x2 = y2 = 0;

                    }
                }
                repaint();
                game.setGameOver(game.isSolved());
                if (game.isGameOver()) {
                    if (JOptionPane.showConfirmDialog(getParent(), "You are win!!!\n New game?", "", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        game.newGame();
                        repaint();
                    }
                }
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int i = 0;
        for (int x = 0; x < wight; x = x + wight / size) {
            for (int y = 0; y < height; y = y + height / size) {
                g.drawImage(game.getImage(game.getPuzzles(i)), x, y, wight / size, height / size, this);
                ++i;
            }
        }
    }

    void setGame(Pieces game) {
        this.game = game;
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class Main extends JFrame {


    private Main() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                else
                    return super.isCellEditable(row, column);
            }

            public Class getColumnClass(int column) {
                return Cell.class;
            }
        };
        JTable table = new JTable(tableModel);
        JScrollPane mainPane = new JScrollPane(table);
        mainPane.setVisible(true);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mainPane.setPreferredSize(new Dimension(800, 500));
        setContentPane(mainPane);
        Vector<Integer> rows = new Vector<>();
        for (int i = 1; i < 40; i++) {
            rows.add(i);
        }
        tableModel.addColumn("", rows);
        for (int i = 0; i < 26; i++) {
            tableModel.addColumn((char) ('A' + i));
            table.getColumnModel().getColumn(i);
        }
        Controller editor = new Controller();
        table.setDefaultEditor(Cell.class, editor);

        setVisible(true);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        Main window = new Main();

    }
}

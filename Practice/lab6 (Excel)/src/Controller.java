import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Controller extends AbstractCellEditor implements TableCellEditor {


    private final static Pattern DATE = Pattern.compile("((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])).02.29))|(((19|2[0-9])[0-9]{2}).02.(0[1-9]|1[0-9]|2[0-8]))|((19|2[0-9])[0-9]{2}).(0[13578]|10|12).((0[1-9]|[12][0-9]|3[01]))|(((19|2[0-9])[0-9]{2}).(0[469]|11).(0[1-9]|[12][0-9]|30))");
    private final static Pattern FORMULA = Pattern.compile("[A-Z][1-9]+[0-9]*");

    private JTextField editor;
    private Cell currentCell;
    private JTable table;
    private int row;
    private int column;


    Controller() {
        super();
        editor = new JTextField();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentCell = (Cell) value;
        this.table = table;
        this.column = column;
        this.row = row;

        if (currentCell != null) {
            editor.setText(currentCell.getContent());
        } else {
            editor.setText("");
        }
        return editor;
    }

    public Object getCellEditorValue() {
        String str = editor.getText();
        if (str.equals(""))
            return null;
        else {
            try {
                switch (str.substring(0, 1)) {
                    case "m": {
                        currentCell = max_min(str);
                        currentCell.setContent(str);
                        break;
                    }
                    case "=": {
                        currentCell = plus_minus(str);
                        currentCell.setContent(str);
                        break;
                    }
                    default: {
                        if (DATE.matcher(str).matches()) currentCell = getDateFromFormula(str);
                        else throw new MyException("Input correct date, please!");
                        currentCell.setContent(str);
                    }
                }
                table.getModel().setValueAt(currentCell, row, column);

            } catch (MyException | NumberFormatException | StringIndexOutOfBoundsException | ClassCastException | ArrayIndexOutOfBoundsException exc) {
                JOptionPane.showMessageDialog(null, "Input correct date, please!");
            }
            return currentCell;
        }
    }

    private Cell max_min(String str) throws MyException, NumberFormatException {

        ArrayList<Cell> container = new ArrayList<>();
        Matcher m;

        m = DATE.matcher(str);
        while (m.find()) {
            container.add(getDateFromFormula(m.group()));
        }
        m = FORMULA.matcher(str);
        while (m.find()) {
            container.add(getCellFromFormula(m.group()));
        }

        boolean equals = str.substring(str.length() - 1).equals(")");
        if (str.substring(0, 4).equals("min(") && equals && !container.isEmpty()) {
            return Collections.min(container);
        } else if (str.substring(0, 4).equals("max(") && equals && !container.isEmpty()) {
            return Collections.max(container);
        } else throw new MyException("Input correct date, please!");
    }

    private Cell plus_minus(String str) throws MyException, NumberFormatException {
        Cell operand1;
        int operand2;
        Matcher mcell = FORMULA.matcher(str.split("[=]|[-]|[+]")[1]);
        if (mcell.matches()) {
            operand1 = getCellFromFormula(str.split("[=]|[-]|[+]")[1]);
            operand2 = Integer.parseInt(str.substring(str.split("[=]|[-]|[+]")[1].length() + 2));
            if (str.indexOf("+") > 0) {
                operand1.add(Calendar.DAY_OF_MONTH, operand2);
            } else if (str.indexOf("-") > 0) {
                operand2 *= -1;
                operand1.add(Calendar.DAY_OF_MONTH, operand2);
            } else throw new MyException("Input correct date, please!");
            return operand1;
        } else if (str.length() > 11) {
            Matcher mdate = DATE.matcher(str.substring(1, 11));
            if (mdate.matches()) {
                operand1 = getDateFromFormula(str.substring(1, 11));
                operand2 = Integer.parseInt(str.substring(12));
                if (str.substring(11, 12).equals("+"))
                    operand1.add(Calendar.DAY_OF_MONTH, operand2);
                else if (str.substring(11, 12).equals("-")) {
                    operand2 *= -1;
                    operand1.add(Calendar.DAY_OF_MONTH, operand2);
                } else throw new MyException("Input correct date, please!");
                return operand1;
            } else throw new MyException("Input correct date, please!");
        } else throw new MyException("Input correct date, please!");
    }

    private Cell getDateFromFormula(String str) throws NumberFormatException {
        return new Cell(Integer.parseInt(str.substring(0, 4)),
                Integer.parseInt(str.substring(5, 7)) - 1,
                Integer.parseInt(str.substring(8, 10)));
    }


    private Cell getCellFromFormula(String str) throws NumberFormatException, MyException {
        int currentColumn, currentRow;
        currentColumn = str.charAt(0) - 'A' + 1;
        currentRow = Integer.parseInt(str.substring(1)) - 1;
        if (currentRow >= table.getRowCount() || currentColumn >= table.getColumnCount())
            throw new MyException("You're out of the table!");
        if (currentColumn == column && currentRow == row)
            throw new MyException("The loop link!");
        if (table.getValueAt(currentRow, currentColumn) == null)
            throw new MyException("Selected cells are empty!");

        return new Cell((Cell) table.getValueAt(currentRow, currentColumn));

    }

}

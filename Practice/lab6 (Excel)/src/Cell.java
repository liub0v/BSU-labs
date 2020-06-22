import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Cell extends GregorianCalendar {
    private String content;

    Cell(Cell date) {
        super(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        this.content = date.getContent();
    }

    Cell(int year, int month, int day) {
        super(year, month, day);
        this.content = getContent(toString());
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    private String getContent(String s) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
        fmt.setCalendar(this);
        return fmt.format(this.getTime());
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
        fmt.setCalendar(this);
        return fmt.format(this.getTime());
    }
}

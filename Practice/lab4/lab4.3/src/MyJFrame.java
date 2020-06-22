import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class MyJFrame extends JFrame {

    public static void main(String[] args) {
        new MyJFrame();
    }

    private MyJFrame() {
        setVisible(true);
        setPreferredSize(new Dimension(800, 800));
        //чтение информации
        try {
            JsonReader reader = new JsonReader(new FileReader("src/data"));
            Gson g = new Gson();
            Statistic[] statistics = g.fromJson(reader, Statistic[].class);
            for (var i : statistics) {
                if (i.getNumber() < 0)
                    throw new NumberFormatException("incorrect data");
            }
            //создание панели и добавление на неё диаграммы
            DefaultCategoryDataset categoryDataset = dataset(statistics);
            JFreeChart chart = createBarChart(categoryDataset);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new ChartPanel(chart), BorderLayout.CENTER);
            add(panel);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        } catch (IOException | NumberFormatException | JsonSyntaxException exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
        pack();
    }

    // приведение дынных к типу необходимому для создания диаграммы
    private DefaultCategoryDataset dataset(Statistic[] statistics) {
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
        for (int i = 0; i < statistics.length; i++) {
            defaultCategoryDataset.setValue(statistics[i].getNumber(), statistics[i].getCountry(), String.valueOf((i + 1) / 12));
        }
        return defaultCategoryDataset;
    }

    private JFreeChart createBarChart(final CategoryDataset defaultCategoryDataset) {
        // создание диаграммы
        JFreeChart chart = ChartFactory.createBarChart("COVID-19\n TOP-10 countries", null, "Number of infected",
                defaultCategoryDataset, PlotOrientation.VERTICAL, true, true, false);
        // cоздание объекта графической части диаграммы
        CategoryPlot plot = chart.getCategoryPlot();
        // Определение фона
        plot.setBackgroundPaint(Color.lightGray);
        plot.setBackgroundAlpha(0.1f);
        // Цвет сетки на графике
        plot.setRangeGridlinePaint(Color.black);
        // Размещение цифровой оси в левой части диаграммы
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        return chart;
    }
}

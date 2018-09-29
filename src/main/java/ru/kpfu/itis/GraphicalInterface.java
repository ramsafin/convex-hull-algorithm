package ru.kpfu.itis;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Objects;

public class GraphicalInterface {

    private XYSeries[] xySeries;
    private XYSeries xySeriesOther;
    private XYSeriesCollection collection;
    private JFreeChart chart;
    private List<Point> points;
    private List<Point> hull;

    public GraphicalInterface(List<Point> points, List<Point> hull) {

        Objects.requireNonNull(points);

        this.points = points;
        this.hull = hull;
        if (hull.size() == 0) {
            return;
        }
        xySeries = getXySeries();
        xySeriesOther = getXYSeries();
        collection = getXYSeriesCollection();
        chart = getChart();
        setUp();
    }

    private XYSeries[] getXySeries() {

        final XYSeries[] xySeries = new XYSeries[hull.size()];

        for (int i = 0; i < xySeries.length - 1; i++) {
            ru.kpfu.itis.Point p1 = hull.get(i);
            ru.kpfu.itis.Point p2 = hull.get(i + 1);

            xySeries[i] = new XYSeries(i + 1);

            xySeries[i].add(p1.getX(), p1.getY());
            xySeries[i].add(p2.getX(), p2.getY());

        }
        xySeries[hull.size() - 1] = new XYSeries(xySeries.length);

        xySeries[hull.size() - 1].add(hull.get(0).getX(), hull.get(0).getY());

        xySeries[hull.size() - 1].add(hull.get(hull.size() - 1).getX()
                , hull.get(hull.size() - 1).getY());

        return xySeries;
    }

    private XYSeries getXYSeries() {

        XYSeries series = new XYSeries("");

        for (Point val : points) {
            series.add(val.getX(), val.getY());
        }
        return series;
    }

    private XYSeriesCollection getXYSeriesCollection() {

        XYSeriesCollection col = new XYSeriesCollection();

        for (XYSeries xySer : xySeries) {
            col.addSeries(xySer);
        }

        col.addSeries(xySeriesOther);

        return col;
    }


    private JFreeChart getChart() {

        return ChartFactory.createXYLineChart("A Hull", "X", "Y", collection,
                PlotOrientation.VERTICAL, false, false, false);
    }

    private void setUp() {

        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(Color.WHITE);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();

        for (int i = 0; i < xySeries.length; i++) {
            renderer.setSeriesPaint(i, Color.red);
        }
        renderer.setSeriesShape(xySeries.length, new Ellipse2D.Double(0, -2, 3, 3));
        renderer.setSeriesShapesVisible(xySeries.length, true);
        renderer.setSeriesPaint(xySeries.length, Color.BLUE);
        renderer.setSeriesLinesVisible(xySeries.length, false);
        renderer.setDrawOutlines(true);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeIncludesZero(false);

        plot.setRangeGridlinePaint(Color.black);
        plot.setDomainGridlinePaint(Color.black);


        final NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setAutoRange(true);
        xAxis.setAutoRangeIncludesZero(false);

    }

    public void paint() {

        if (hull.size() == 0) {
            return;
        }

        JFrame frame = new JFrame("Convex Hull - Graham Algorithm");

        frame.getContentPane().add(new ChartPanel(chart));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setBackground(Color.WHITE);

        frame.setSize(700, 500);

        frame.setVisible(true);
    }
}

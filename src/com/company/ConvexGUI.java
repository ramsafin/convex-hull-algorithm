package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class ConvexGUI {

    private XYSeries [] xySeries;
    private XYSeriesCollection collection;
    private JFreeChart chart;
    private List<Point> points;

    public  ConvexGUI(List<Point> points) {
        if (points == null){
            throw new NullPointerException("Points are null");
        }
        this.points = points;
        xySeries = getXySeries();
        collection = getXYSeriesCollection();
        chart = getChart();
    }

    private XYSeries[] getXySeries(){
        final XYSeries [] xySeries = new XYSeries[points.size()];

        for (int i = 0; i < xySeries.length-1 ; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i+1);

            xySeries[i] = new XYSeries(i+1);

            xySeries[i].add(p1.getX(), p1.getY());
            xySeries[i].add(p2.getX(),p2.getY());

        }
        xySeries[points.size()-1] = new XYSeries(xySeries.length);

        xySeries[points.size()-1].add(points.get(0).getX(), points.get(0).getY());

        xySeries[points.size()-1].add(points.get(points.size()-1).getX()
                ,points.get(points.size()-1).getY());

        return xySeries;
    }

    private XYSeriesCollection getXYSeriesCollection(){

        XYSeriesCollection col = new XYSeriesCollection();

        for(XYSeries xySer : xySeries){
            col.addSeries(xySer);
        }

        return col;
    }


    private JFreeChart getChart(){

        JFreeChart jFreeChart = ChartFactory.createXYLineChart("ОБОЛОЧКА", "X", "Y", collection,
                PlotOrientation.VERTICAL, true, true, false);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) jFreeChart.getXYPlot().getRenderer();

        for (int i = 0; i < xySeries.length; i++) {
            renderer.setSeriesPaint(i,Color.BLACK);
            renderer.setSeriesShape(i,new Ellipse2D.Double(0,0,3,3));
            renderer.setSeriesShapesVisible(i,true);

        }

        return jFreeChart;
    }

    public void paint(){
        if (points.size() == 0){
            return;
        }

        JFrame frame = new JFrame("convex hull");

        frame.getContentPane().add(new ChartPanel(chart));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setBackground(Color.WHITE);

        frame.setSize(500, 500);

        frame.setVisible(true);
    }
}

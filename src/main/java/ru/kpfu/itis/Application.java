package ru.kpfu.itis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static List<Point> readPointsFromFile(String path) {

        List<Point> points = new ArrayList<>();

        File file = new File(path);

        try (Scanner sc = new Scanner(file)) {

            double xCoordinate;

            double yCoordinate;

            String s;

            while (sc.hasNext()) {

                s = sc.next();
                xCoordinate = Double.parseDouble(s);

                if (sc.hasNext()) {
                    s = sc.next();
                    yCoordinate = Double.parseDouble(s);
                    points.add(new Point(xCoordinate, yCoordinate));

                } else {
                    throw new WrongDataInputException("Given an incorrect set of points!");
                }
            }

        } catch (FileNotFoundException | WrongDataInputException e) {
            e.printStackTrace();
        }
        return points;
    }


    public static void writeToFile(String path, List<Point> points) {

        File file = new File(path);

        try (PrintWriter pw = new PrintWriter(file)) {

            points.forEach(e -> {
                pw.print(e.getX() + " ");
                pw.println(e.getY());
            });
            pw.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws URISyntaxException {

        // in resources
        Path inputFile = Paths.get(ClassLoader.getSystemResource("InitialPoints.txt").toURI());

        List<Point> points = readPointsFromFile(inputFile.toAbsolutePath().toString());

        ConvexHull convexHull = new ConvexHull(points);

        List<Point> hull = convexHull.findConvexHull();

        GraphicalInterface gui = new GraphicalInterface(points, hull);

        gui.paint();

    }
}

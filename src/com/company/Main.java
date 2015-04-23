package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * @param path - путь до файла
     * @return список точек из файла
     */
    public static List<Point> readPointsFromFile(String path){

        List<Point> points = new ArrayList<>();
        File file = new File(path);

        try(Scanner sc = new Scanner(file)){

            double xCoordinate;//x - координата

            double yCoordinate;//y - координата

            String s;
            while (sc.hasNext()){

                s = sc.next();
                xCoordinate = Double.parseDouble(s);

                if (sc.hasNext()){
                    s = sc.next();
                    yCoordinate = Double.parseDouble(s);
                    points.add(new Point(xCoordinate,yCoordinate));

                }else {
                    throw new WrongDataInputException("Неправильно заданы координаты точек");
                }
            }

        } catch (FileNotFoundException | WrongDataInputException e) {
            e.printStackTrace();
        }
        return points;
    }


    /**
     * Метод для записи в файл списка точек
     * @param path - путь до файла, в который пишем
     * @param points - список точек
     */
    public static void writeToFile(String path, List<Point> points){

        File file = new File(path);

        try(PrintWriter pw = new PrintWriter(file)){

            points.forEach(e->{
                pw.print(e.getX()+" ");
                pw.println(e.getY());
            });
            pw.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        if (args == null){
            throw new NullPointerException("args cant be null. Enter path to input and output");
        }

        if (args.length < 2){
            System.err.println("Arguments must be strictly two");
            return;
        }

        List<Point> points = readPointsFromFile(args[0]);

        ConvexHull convexHull = new ConvexHull(points);

        List<Point> hull = convexHull.findConvexHull(); // Convex hull

        writeToFile(args[1], hull);

        ConvexGUI gui = new ConvexGUI(points,hull);

        gui.paint();

    }
}

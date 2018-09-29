package ru.kpfu.itis;

import java.util.Comparator;

import static java.lang.Math.*;


public class PointHelper {

    private static double computeDet(Point p1, Point p2, Point p3){

        return (p2.getX() - p1.getX()) * (p3.getY() - p1.getY())
                - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX());
    }


    public static boolean isClockwise(Point p1, Point p2, Point p3) {

        return computeDet(p1, p2, p3) < 0;
    }


    public static Comparator<Point> polarOrderComparator(){
        return (p1, p2) -> {

            int diff = Double.compare(polarAngle(p1),polarAngle(p2));
            if (diff == 0){
                return Double.compare(distance(p1),distance(p2));
            }
            return diff;
        };
    }

    private static double distance(Point p){

        return pow(p.getX(), 2) + pow(p.getY(), 2);
    }


    private static double polarAngle(Point p){

        double angle = atan2(p.getY(), p.getX());

        return angle < 0 ? angle + 2 * PI : angle;
    }

}

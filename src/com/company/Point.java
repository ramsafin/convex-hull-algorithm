package com.company;


/**
 * Класс Point - точка
 * Обладает x - координатой и y - координатой
 */
public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {

        return x;
    }

    public double getY() {

        return y;
    }

    /**
     * @param point - точка, которую вычитаем
     */
    public void minus(Point point){

        x -= point.x;

        y -= point.y;
    }

    /**
     * @param point - точку, которую прибавляем
     */
    public void plus(Point point){

        x += point.x;

        y += point.y;
    }


    @Override
    public String toString() {

        return "(" + x + "; " + y+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}

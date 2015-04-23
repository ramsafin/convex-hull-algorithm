package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Класс ConvexHull - оболочка
 * Содержит список точек
 */
public class ConvexHull {

    private List<Point> points; // список точек, образующие оболочку

    public ConvexHull(List<Point> points) {
        if (points == null){
            throw new NullPointerException("Points are null");
        }
        this.points = points;
    }


    /**
     * Удаляет повторяющиеся точки
     */

    private void removeDuplicates(){

        boolean indexesForRemove[] = new boolean[points.size()];

        for (int i = 0; i < points.size()-1 ; i++) {

            for (int j = i+1; j < points.size() ; j++) {

                if (points.get(i).equals(points.get(j))){
                    indexesForRemove[j] = true;
                }
            }
        }
        int index = 0;
        for (int i = 0; i < indexesForRemove.length; i++) {
            if (indexesForRemove[i]){
                points.remove(i-index);
                index++;
            }
        }
    }



    /**
     * @return список точек в порядке обхода против часовой стрелки, образующий минимальную оболочку
     * Алгоритм Грэхема
     */
    public List<Point> findConvexHull(){

        MyLinkedStack<Point> stack = new MyLinkedStack<>();

        if (points.size() == 0){
            return new ArrayList<>();
        }

        init(); //сортируем по полярному углу, удаляем лишние точки

        if (points.size() <= 3){
            return points;
        }

        stack.push(points.get(0));
        stack.push(points.get(1));

        for (int i = 2; i < points.size(); i++) {

            while (stack.size() >= 2 && PointHelper.isClockwise(stack.nextToTop(),stack.peek(),points.get(i))){
                //удаляем из стэка точку, так как образуется правый поворот
                stack.pop();
            }

            //добавляем новую точку, которая идет по порядку
            stack.push(points.get(i));
        }
        return stack.toList();
    }

    /**
     * Метод, который сортирует точки по полярному углу, удаляет повторяющиеся точки
     */
    private void init(){
        //удаляем повторяющиеся точки
        removeDuplicates();

        //находим минимальную точку
        Point min = getMinimumPoint();

        Point minClone = new Point(min.getX(),min.getY());

        //переносим начало координат в точку минимума
        points.stream().forEach(e -> e.minus(minClone));

        //сортируем точки по полярному углу относительно точки min
        Collections.sort(points, PointHelper.polarOrder());

        //возвращаем координаты точек
        points.stream().forEach(e -> e.plus(minClone));
    }

    /**
     * метод для поиска минимальной точки в списке точек
     * @return точку с минимальной y - координатой
     * или самую левую, если таких несколько
     */
    private Point getMinimumPoint(){
        Point min = points.get(0);
        for (Point val : points){
            //сравниваем по y координатам
            int diff = Double.compare(min.getY(), val.getY());

            if (diff > 0){
                min = val;

            }else if (diff == 0){
                //иначе сравниваем по x координатам
                diff = Double.compare(min.getX(), val.getX());

                if (diff > 0){
                    min = val;
                }
            }
        }
        return min;
    }
}

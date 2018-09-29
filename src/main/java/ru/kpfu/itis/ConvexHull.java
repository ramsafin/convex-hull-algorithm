package ru.kpfu.itis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ConvexHull {

    private List<Point> points;

    public ConvexHull(List<Point> points) {
        Objects.requireNonNull(points);
        this.points = points;
    }

    private void removeDuplicates() {

        boolean indexesForRemove[] = new boolean[points.size()];

        for (int i = 0; i < points.size() - 1; i++) {

            for (int j = i + 1; j < points.size(); j++) {

                if (points.get(i).equals(points.get(j))) {
                    indexesForRemove[j] = true;
                }
            }
        }
        int index = 0;

        for (int i = 0; i < indexesForRemove.length; i++) {
            if (indexesForRemove[i]) {
                points.remove(i - index);
                index++;
            }
        }
    }


    /**
     * Graham Algorithm
     */
    public List<Point> findConvexHull() {

        MyLinkedStack<Point> stack = new MyLinkedStack<>();

        if (points.size() == 0) {
            return new ArrayList<>();
        }

        init();

        if (points.size() <= 3) {
            return points;
        }

        stack.push(points.get(0));
        stack.push(points.get(1));

        for (int i = 2; i < points.size(); i++) {

            while (stack.size() >= 2 && PointHelper.isClockwise(stack.nextToTop(), stack.peek(), points.get(i))) {
                stack.pop();
            }
            stack.push(points.get(i));
        }
        return stack.toList();
    }


    private void init() {

        removeDuplicates();

        Point min = getMinimumPoint();

        points.forEach(e -> e.minus(min));

        points.sort(PointHelper.polarOrderComparator());

        points.forEach(e -> e.plus(min));
    }

    private Point getMinimumPoint() {

        Point min = points.get(0);

        for (Point val : points) {

            int diff = Double.compare(min.getY(), val.getY());

            if (diff > 0) {
                min = val;

            } else if (diff == 0) {

                diff = Double.compare(min.getX(), val.getX());

                if (diff > 0) {
                    min = val;
                }
            }
        }
        return new Point(min.getX(), min.getY());
    }
}


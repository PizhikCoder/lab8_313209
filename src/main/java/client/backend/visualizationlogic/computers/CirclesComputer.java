package client.backend.visualizationlogic.computers;


import client.backend.visualizationlogic.entities.StraightLineEquation;
import javafx.geometry.Point2D;

public class CirclesComputer {
    private final int CIRCLE_POLYGON_POINTS_COUNT = 100;

    public boolean checkCollision(Point2D center1, Point2D center2, float radius1, float radius2) {
        double distance = center1.distance(center2);
        if (radius1 + radius2 <= distance) {
            return false;
        }
        if (radius1 + radius2 > distance) {
            if (distance < Math.max(radius1, radius2) && distance + Math.min(radius1, radius2) < Math.max(radius1, radius2)) {
                return false;
            }
            if (Math.max(radius1, radius2) > Math.min(radius1, radius2) + distance) {
                return false;
            }
            return true;
        }
        return false;
    }

    public StraightLineEquation findSecantEquation(Point2D center1, Point2D center2, float radius1, float radius2) {
        double a = -2 * (center2.getX() - center1.getX());
        double b = -2 * (center2.getY() - center1.getY());
        double c = Math.pow(center2.getX(), 2) + Math.pow(center2.getY(), 2) + radius1 * radius1 - radius2 * radius2 - Math.pow(center1.getX(), 2) - Math.pow(center1.getY(), 2);
        return new StraightLineEquation(a, b, c);
    }

    public Point2D[] calculateCirclePolygon(Point2D center, float radius) {
        Point2D[] points = new Point2D[CIRCLE_POLYGON_POINTS_COUNT];
        double length = 2 * Math.PI * radius;
        double partLength = length / 100;
        double area = partLength * 0.5 * radius;
        double alpha = (area * 360) / (Math.PI * radius * radius);
        double step = Math.sin(alpha);

        int counter = 0;
        double x = 0;

        while (counter<CIRCLE_POLYGON_POINTS_COUNT){
            double y = Math.sqrt(radius*radius - Math.pow(x - center.getX(), 2)) + center.getY();
            points[counter] = new Point2D(x, y);
            x+=step;
            counter++;
        }
        return points;
    }
}

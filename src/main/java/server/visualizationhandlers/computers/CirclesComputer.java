package server.visualizationhandlers.computers;


import javafx.geometry.Point2D;

public class CirclesComputer {

    public boolean checkCollision(Point2D center1, Point2D center2, float radius1, float radius2) {
        double distance = center1.distance(center2);
        if (radius1 + radius2 > distance) {
            if (Math.max(radius1, radius2) > distance){
                return true;
            }
        }
        return false;
    }
}

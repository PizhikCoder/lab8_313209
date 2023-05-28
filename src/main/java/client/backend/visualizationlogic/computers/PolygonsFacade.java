package client.backend.visualizationlogic.computers;


import client.backend.visualizationlogic.entities.MusicBandPolygon;
import client.backend.visualizationlogic.entities.StraightLineEquation;
import javafx.geometry.Point2D;

public class PolygonsFacade {
    public MusicBandPolygon[] checkPolygons(MusicBandPolygon polygon1, MusicBandPolygon polygon2)  {
        Point2D[] points1 = polygon1.getPointsFromPolygon();
        Point2D[] points2 = polygon2.getPointsFromPolygon();

        CirclesComputer circlesComputer = new CirclesComputer();
        if (circlesComputer.checkCollision(polygon1.getCenter(), polygon2.getCenter(), polygon1.getRadius(), polygon2.getRadius())){
            StraightLineEquation straightLineEquation = circlesComputer.findSecantEquation(polygon1.getCenter(), polygon2.getCenter(), polygon1.getRadius(), polygon2.getRadius());
            checkPoints(points1, polygon1.getCenter(), straightLineEquation);
            checkPoints(points2, polygon2.getCenter(), straightLineEquation);
        }
        polygon1.updatePoints(points1);
        polygon2.updatePoints(points2);
        return new MusicBandPolygon[]{polygon1, polygon2};
    }

    public MusicBandPolygon checkBorder(MusicBandPolygon polygon1, StraightLineEquation straightLineEquation)  {
        Point2D[] points1 = polygon1.getPointsFromPolygon();

        checkPoints(points1, polygon1.getCenter(), straightLineEquation);
        polygon1.updatePoints(points1);
        return polygon1;
    }

    private void checkPoints(Point2D[] points, Point2D center, StraightLineEquation straightLineEquation){
        PointsComputer pointsComputer = new PointsComputer();
        for(int i = 0; i < points.length; i++){
            if (!pointsComputer.isPointAndCenterTogether(center, points[i], straightLineEquation)){
                points[i] = pointsComputer.projectPoint(points[i], straightLineEquation);
            }
        }
    }

}

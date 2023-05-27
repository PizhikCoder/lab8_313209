package client.backend.visualizationlogic;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import shared.core.models.MusicBand;

import java.util.List;

public class MusicBandPolygon extends Polygon {
    private final MusicBand musicBand;
    private final Point2D center;
    private final float RADIUS;

    public MusicBandPolygon(MusicBand musicBand, Point2D center, float radius) {
        this.musicBand = musicBand;
        this.center = center;
        this.RADIUS = radius;
    }

    public void updatePoints(Point2D[] points) {
        List<Double> pointsCollection = getPoints();
        pointsCollection.clear();
        for (int i = 0; i < points.length; i++) {
            pointsCollection.add(points[i].getX());
            pointsCollection.add(points[i].getY());
        }
    }

    public Point2D[] getPointsFromPolygon() {
        Point2D[] output = new Point2D[getPoints().size() / 2];
        for (int i = 0; i < getPoints().size(); i += 2) {
            output[i / 2] = new Point2D(getPoints().get(i), getPoints().get(i + 1));
        }
        return output;
    }

    public Point2D getCenter() {
        return center;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }


    public float getRadius() {
        return RADIUS;
    }
}

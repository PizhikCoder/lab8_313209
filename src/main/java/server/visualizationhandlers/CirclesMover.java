package server.visualizationhandlers;


import javafx.geometry.Point2D;
import server.commands.Command;
import server.commands.UpdateCommand;
import server.core.Invoker;
import server.core.managers.ModelsManager;
import server.core.validators.ModelsValidator;
import server.visualizationhandlers.computers.CirclesComputer;
import shared.commands.enums.DataField;
import shared.connection.requests.UpdateModelServerRequest;
import shared.core.models.Coordinates;
import shared.core.models.MusicBand;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CirclesMover {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ArrayDeque<MusicBand> collection;

    private final ModelsManager modelsManager;

    private final Invoker invoker;

    private final int SPEED_VECTOR_X_INDEX = 0;

    private final int SPEED_VECTOR_Y_INDEX = 1;

    private final float DEFAULT_RADIUS_VALUE = 100;

    private final int X_SPEED_MULTIPLIER = 1;

    private final int Y_SPEED_MULTIPLIER = 1;

    private final int VELOCITY1_INDEX = 0;

    private final int VELOCITY2_INDEX = 1;

    private final int MOVES_PER_SECOND = 55;

    private final int SECOND = 1000;


    public CirclesMover(ModelsManager modelsManager, Invoker invoker){
        this.collection = modelsManager.getModels();
        this.invoker = invoker;
        this.modelsManager = modelsManager;
    }

    public void startMoving(MusicBand musicBand1) throws InterruptedException {
        double[] velocity1 = null, velocity2 = null;
        for (MusicBand musicBand2 : collection){
            if (musicBand2 == musicBand1) continue;
            boolean isMoved = false;
            logger.log(Level.INFO, String.format("Starting %s and %s checking.", musicBand1.getId(), musicBand2.getId()));
            double[][] velocities = computeVelocities(musicBand1, musicBand2);
            velocity1 = velocities[VELOCITY1_INDEX];
            velocity2 = velocities[VELOCITY2_INDEX];
            while (checkCollision(musicBand1, musicBand2)){
                isMoved = true;
                move(musicBand1, velocity1);
                move(musicBand2, velocity2);
                Thread.sleep(SECOND/MOVES_PER_SECOND);
            }
            if (isMoved) {
                ForkJoinTask<Void> task1 = createForkTask(musicBand1);
                ForkJoinTask<Void> task2 = createForkTask(musicBand2);
                task1.fork();
                task2.fork();
                task1.join();
                task2.join();
                logger.log(Level.INFO, String.format("Models %s and %s moved!", musicBand1.getId(), musicBand2.getId()));
                break;
            }
        }
    }

    private ForkJoinTask<Void> createForkTask(MusicBand musicBand){
        return new RecursiveAction() {
            @Override
            protected void compute() {
                try {
                    startMoving(musicBand);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Exception in fork!", e);
                }
            }
        };
    }

    private void move(MusicBand musicBand, double[] velocity){
        Coordinates coordinates = new Coordinates(musicBand.getCoordinates().getX(), musicBand.getCoordinates().getY());
        if (coordinates.getX() + Math.round(velocity[SPEED_VECTOR_X_INDEX]) <= 0 || coordinates.getY() + velocity[SPEED_VECTOR_Y_INDEX]<0){
            return;
        }
        coordinates.setX((int)(coordinates.getX() + Math.round(velocity[SPEED_VECTOR_X_INDEX])));
        coordinates.setY(coordinates.getY() + velocity[SPEED_VECTOR_Y_INDEX]);
        Map<DataField, Object> data = musicBand.toHashMap();
        data.put(DataField.COORDINATES, coordinates);
        if (ModelsValidator.idExist(musicBand.getId()) && invoker.getDatabaseHandler().updateModel(data, musicBand.getId())) {
            modelsManager.updateModel(musicBand.getId(), data, invoker.getPrinter());
            invoker.getConnection().sendChangesToAll(new UpdateModelServerRequest(musicBand));
        }
    }

    private boolean checkCollision(MusicBand musicBand1, MusicBand musicBand2){
        Point2D center1 = new Point2D(musicBand1.getCoordinates().getX(), musicBand1.getCoordinates().getY());
        Point2D center2 = new Point2D(musicBand2.getCoordinates().getX(), musicBand2.getCoordinates().getY());
        float radius1 = musicBand1.getFrontMan().getHeight() == null ? DEFAULT_RADIUS_VALUE : musicBand1.getFrontMan().getHeight();
        float radius2 = musicBand2.getFrontMan().getHeight() == null ? DEFAULT_RADIUS_VALUE : musicBand2.getFrontMan().getHeight();
        return new CirclesComputer().checkCollision(center1, center2, radius1 ,radius2);
    }

    private double[][] computeVelocities(MusicBand musicBand1, MusicBand musicBand2){
        double[] velocity1 = new double[2], velocity2 = new double[2];
        Point2D center1 = new Point2D(musicBand1.getCoordinates().getX(), musicBand1.getCoordinates().getY());
        Point2D center2 = new Point2D(musicBand2.getCoordinates().getX(), musicBand2.getCoordinates().getY());
        double[] speedVector = computeSpeed(center1, center2);
        if (center1.distance(center2) < new Point2D(center1.getX() + speedVector[SPEED_VECTOR_X_INDEX], center1.getY() + speedVector[SPEED_VECTOR_Y_INDEX]).distance(center2)){
            velocity1 = Arrays.copyOf(speedVector, speedVector.length);
            speedVector[SPEED_VECTOR_X_INDEX] *= -1;
            speedVector[SPEED_VECTOR_Y_INDEX] *= -1;
            velocity2 = Arrays.copyOf(speedVector, speedVector.length);
        }
        else {
            velocity2 = Arrays.copyOf(speedVector, speedVector.length);
            speedVector[SPEED_VECTOR_X_INDEX] *= -1;
            speedVector[SPEED_VECTOR_Y_INDEX] *= -1;
            velocity1 = Arrays.copyOf(speedVector, speedVector.length);
        }
        return new double[][]{
                velocity1,
                velocity2
        };
    }

    private double[] computeSpeed(Point2D center1, Point2D center2){
        double dx = center1.getX() - center2.getX();
        double dy = center1.getY() - center2.getY();
        double multiplier = 1/(Math.min(dx, dy) == 0 ? 1 : Math.min(dx, dy));
        double xVelocity = dx * multiplier * X_SPEED_MULTIPLIER;
        double yVelocity = dy * multiplier * Y_SPEED_MULTIPLIER;
        if (yVelocity == xVelocity && xVelocity == 0){
            yVelocity = 1;
            xVelocity = 1;
        }

        return new double[]{xVelocity, yVelocity};
    }
}

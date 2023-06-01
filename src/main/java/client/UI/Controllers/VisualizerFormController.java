package client.UI.Controllers;

import client.MainApplication;
import client.UI.resourcebundles.enums.MusicBandCreatingAndUpdatingFormElements;
import client.UI.resourcebundles.enums.VisualisationFirmElements;
import client.backend.commands.Command;
import client.backend.commands.UpdateCommand;
import client.backend.core.Invoker;
import client.backend.core.MusicBandFieldsValidators;
import client.backend.visualizationlogic.entities.MusicBandPolygon;
import client.backend.visualizationlogic.computers.CirclesComputer;
import client.backend.visualizationlogic.computers.PolygonsFacade;
import client.backend.visualizationlogic.entities.StraightLineEquation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.util.Duration;
import shared.commands.enums.DataField;
import shared.core.ClientInfo;
import shared.core.models.Coordinates;
import shared.core.models.MusicBand;
import shared.core.models.Person;

import java.util.*;

public class VisualizerFormController {
    @FXML
    private Label coordinateXLabel;

    @FXML
    private Scene mainScene;

    @FXML
    private Label coordinateYLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private TextField coordinateXTextField;

    @FXML
    private TextField coordinateYTextField;

    @FXML
    private Slider heightSlider;

    @FXML
    private Button backToTableButton;

    @FXML
    private Pane canvasPane;

    @FXML
    private Label canvasXLabel;

    @FXML
    private Label canvasYLabel;


    private final float HEIGHT_SLIDER_DEFAULT_VALUE = 1;

    private static Canvas canvas = new Canvas();

    private static Timeline frameTimer;

    private final static double SCALE_COMPUTING_RADIUS_MULTIPLIER = 0.67;

    private static final Affine defaultTransform = canvas.getGraphicsContext2D().getTransform();

    private ArrayList<MusicBand> collection;

    private final int CIRCLE_POLYGON_POINTS_COUNT = 100;


    private static final int CANVAS_DEFAULT_WIDTH = 580;

    private static final int CANVAS_DEFAULT_HEIGHT = 580;

    private static final int DEFAULT_STROKE_WIDTH = 10;

    private final float DEFAULT_RADIUS_VALUE = 100;

    private final double FRAMES_PER_SECOND = 60;

    private final int SECOND = 1000;

    private ArrayList<MusicBandPolygon> musicBandPolygons;

    private MusicBand selectedMusicBand;



    static {
        configureCanvas();
    }

    @FXML
    public void initialize() {
        musicBandPolygons = new ArrayList<>();
        MainFormController.getCurrentLocale().addListener(change->updateLocale());
        updateLocale();
        canvas.setOnMouseClicked(this::onCanvasMouseClicked);
        canvas.setOnMouseMoved(this::onCanvasMouseMoved);
        canvasPane.getChildren().add(canvas);
        frameTimer = new Timeline(new KeyFrame(Duration.millis(0)), new KeyFrame(
                Duration.millis(SECOND / FRAMES_PER_SECOND),
                actionEvent -> {
                    collection = new ArrayList<>(MainFormController.getMainFormController().getModelsCollection());
                    this.drawModels();
                }
        ));
        frameTimer.playFromStart();
    }

    private void updateLocale(){
        coordinateXLabel.setText(VisualisationFirmElements.COORDINATE_X_LABEL.toString());
        coordinateYLabel.setText(VisualisationFirmElements.COORDINATE_Y_LABEL.toString());
        heightLabel.setText(VisualisationFirmElements.PERSON_HEIGHT_LABEL.toString());
        backToTableButton.setText(VisualisationFirmElements.BACK_TO_THE_TABLE_BUTTON.toString());
    }

    private static void configureCanvas() {
        canvas.maxHeight(Double.POSITIVE_INFINITY);
        canvas.maxWidth(Double.POSITIVE_INFINITY);
        canvas.setHeight(CANVAS_DEFAULT_HEIGHT);
        canvas.setWidth(CANVAS_DEFAULT_WIDTH);
        canvas.setScaleY(1);
        canvas.setScaleX(1);
        canvas.getGraphicsContext2D().setLineWidth(DEFAULT_STROKE_WIDTH);
    }

    private void drawModels() {
        if (collection.isEmpty()) return;
        canvas.getGraphicsContext2D().setTransform(defaultTransform);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        computeCanvasScale();
        initPolygons();
        musicBandPolygons.sort((o1, o2) -> Float.compare(o1.getRadius(), o2.getRadius()));
        for (MusicBandPolygon polygon : musicBandPolygons) {
            drawModel(polygon);
        }
        frameTimer.playFromStart();
    }

    private void computeCanvasScale() {
        MusicBand[] modelsArray = collection.toArray(MusicBand[]::new);

        double canvasComputedWidth = Arrays.stream(modelsArray).mapToDouble(x ->
                x.getCoordinates().getX() + (x.getFrontMan().getHeight() == null ?
                        DEFAULT_RADIUS_VALUE* SCALE_COMPUTING_RADIUS_MULTIPLIER :
                        x.getFrontMan().getHeight() * SCALE_COMPUTING_RADIUS_MULTIPLIER)).max().getAsDouble();

        double canvasComputedHeight = Arrays.stream(modelsArray).mapToDouble(x ->
                x.getCoordinates().getY() + (x.getFrontMan().getHeight() == null ?
                        DEFAULT_RADIUS_VALUE* SCALE_COMPUTING_RADIUS_MULTIPLIER :
                        x.getFrontMan().getHeight() * SCALE_COMPUTING_RADIUS_MULTIPLIER)).max().getAsDouble();

        if (Math.max(canvasComputedHeight, canvasComputedWidth) != 0) {
            canvas.getGraphicsContext2D().scale(canvas.getWidth() / Math.max(canvasComputedHeight, canvasComputedWidth), canvas.getWidth() / Math.max(canvasComputedHeight, canvasComputedWidth));
        }
    }

    private void initPolygons() {
        CirclesComputer circlesComputer = new CirclesComputer();
        musicBandPolygons.clear();
        for (MusicBand musicBand : collection) {
            float radius = musicBand.getFrontMan().getHeight() == null ? DEFAULT_RADIUS_VALUE : musicBand.getFrontMan().getHeight();
            Point2D center = new Point2D(musicBand.getCoordinates().getX(), musicBand.getCoordinates().getY());
            Point2D[] points = circlesComputer.calculateCirclePolygon(center, radius, CIRCLE_POLYGON_POINTS_COUNT);
            MusicBandPolygon musicBandPolygon = new MusicBandPolygon(musicBand, center, radius);
            musicBandPolygon.updatePoints(points);
            musicBandPolygons.add(musicBandPolygon);
        }
    }

    private void drawModel(MusicBandPolygon polygon) {
        PolygonsFacade polygonsFacade = PolygonsFacade.getInstance();
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        for (MusicBandPolygon polygonToCompare : musicBandPolygons) {
            if (polygonToCompare == polygon) continue;
            polygonsFacade.checkPolygons(polygon, polygonToCompare);
            polygonsFacade.checkBorder(polygon, new StraightLineEquation(0, 1, 0));
            polygonsFacade.checkBorder(polygon, new StraightLineEquation(0, 1, -canvas.getHeight() / affine.getMyy()));
            polygonsFacade.checkBorder(polygon, new StraightLineEquation(1, 0, 0));
            polygonsFacade.checkBorder(polygon, new StraightLineEquation(1, 0, -canvas.getWidth() / affine.getMxx()));

        }
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        configureGC(polygon.getMusicBand().getOwnerId());
        Point2D[] points = polygon.getPointsFromPolygon();
        graphicsContext.fillPolygon(Arrays.stream(points).mapToDouble(Point2D::getX).toArray(), Arrays.stream(points).mapToDouble(Point2D::getY).toArray(), points.length);
        graphicsContext.strokePolygon(Arrays.stream(points).mapToDouble(Point2D::getX).toArray(), Arrays.stream(points).mapToDouble(Point2D::getY).toArray(), points.length);
    }

    private void clearResources() {
        canvasPane.getChildren().clear();
        frameTimer.stop();
    }

    private void configureGC(int ownerId) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        if (ownerId == ClientInfo.getUserId()) {
            graphicsContext.setStroke(Color.GREEN);
            graphicsContext.setFill(Color.LIGHTGREEN);
        } else {
            graphicsContext.setStroke(Color.DARKGREY);
            graphicsContext.setFill(Color.LIGHTGREY);
        }
    }

    @FXML
    protected void onCanvasMouseClicked(MouseEvent event) {
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        double newX = event.getX() / affine.getMxx();
        double newY = event.getY() / affine.getMyy();
        MusicBandPolygon musicBandPolygon = null;
        for (MusicBandPolygon polygon : musicBandPolygons) {
            if (polygon.contains(newX, newY)) {
                musicBandPolygon = polygon;
            }
        }
        if (musicBandPolygon == null) {
            resetSelection();
            return;
        }
        selectPolygon(musicBandPolygon);
    }

    @FXML
    protected void onBackToTableButtonPressed(ActionEvent actionEvent){
        MainApplication.getPrimaryStage().setResizable(true);
        MainApplication.getPrimaryStage().setScene(MainFormController.getMainFormController().getPrimaryScene());
        clearResources();
    }

    @FXML
    protected void onOkButtonPressed(ActionEvent actionEvent) {
        if (MusicBandFieldsValidators.coordinateXValidate(coordinateXTextField)
                & MusicBandFieldsValidators.coordinateYValidate(coordinateYTextField)
                && MusicBandFieldsValidators.personHeightCheck(String.valueOf(heightSlider.getValue()))
                && selectedMusicBand != null) {
            Map<DataField, Object> data = selectedMusicBand.toHashMap();
            Coordinates coordinates = new Coordinates(Integer.parseInt(coordinateXTextField.getText()), Double.parseDouble(coordinateYTextField.getText()));
            data.put(DataField.COORDINATES, coordinates);
            Person person = selectedMusicBand.getFrontMan();
            person.setHeight((float) heightSlider.getValue());
            data.put(DataField.FRONTMAN, person);
            Command command = new UpdateCommand(Invoker.getInstance(), selectedMusicBand.getId(), data);
            Invoker.getInstance().invokeCommand(command);
        }
    }

    private void resetSelection() {
        coordinateXTextField.setText("");
        coordinateYTextField.setText("");
        heightSlider.setValue(HEIGHT_SLIDER_DEFAULT_VALUE);
        selectedMusicBand = null;
    }

    private void selectPolygon(MusicBandPolygon musicBandPolygon) {
        coordinateXTextField.setText(String.valueOf(musicBandPolygon.getMusicBand().getCoordinates().getX()));
        coordinateYTextField.setText(String.valueOf(musicBandPolygon.getMusicBand().getCoordinates().getY()));
        Float height = musicBandPolygon.getMusicBand().getFrontMan().getHeight();
        heightSlider.setValue(height == null ? 100 : height);
        selectedMusicBand = musicBandPolygon.getMusicBand();
    }

    @FXML
    protected void onButtonMouseEntered(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 2;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }

    @FXML
    protected void onButtonMouseExited(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 1;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }

    private void onCanvasMouseMoved(MouseEvent mouseEvent){
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        Integer newX = (int)Math.round(mouseEvent.getX() / affine.getMxx());
        Integer newY = (int)Math.round(mouseEvent.getY() / affine.getMyy());
        canvasXLabel.setText(newX.toString());
        canvasYLabel.setText(newY.toString());
    }

}

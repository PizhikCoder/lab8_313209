package fxproject.UI.Controllers;

import fxproject.MainApplication;
import fxproject.backend.commands.Command;
import fxproject.backend.commands.LogOutCommand;
import fxproject.backend.core.Invoker;
import shared.core.models.MusicBand;
import fxproject.backend.tableHandlers.TableViewHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.ArrayList;

public class MainFormController {
    @FXML
    private HBox filtersHBox;

    @FXML
    private TableView tableView;
    @FXML
    private static ObservableList<MusicBand> modelsCollection = FXCollections.observableArrayList();

    @FXML
    private Button removeFiltersButton;

    @FXML
    private Menu userMenu;

    private volatile TableViewHandler tableViewHandler;

    private static MainFormController mainFormController;


    @FXML
    public void initialize() {
        mainFormController = this;
        tableViewHandler = new TableViewHandler(tableView, modelsCollection);
        tableViewHandler.initializeColumns();
    }

    @FXML
    protected void onAddButtonPressed(ActionEvent actionEvent){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MusicBandCreatingFormController.class.getResource("MusicBandCreatingForm.fxml"));
            Parent node = fxmlLoader.load();
            Scene scene = new Scene(node, 400,600);
            Stage stage = new Stage();
            MusicBandCreatingFormController musicBandCreatingFormController = fxmlLoader.getController();
            musicBandCreatingFormController.setCurrentStage(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can not load music band creator form!");
            alert.show();
        }
    }

    @FXML
    protected void onFilterCreatingButtonPressed(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FilterCreator.fxml"));
            Parent node = fxmlLoader.load();
            FilterCreatorController filterCreatorController = fxmlLoader.getController();
            Scene scene = new Scene(node, 400,300);
            Stage stage = new Stage();
            filterCreatorController.setCurrentStage(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can not load filter creator form!");
            alert.show();
        }
    }

    @FXML
    protected void onFilterRemovingButtonPressed(ActionEvent actionEvent) {
        filtersHBox.getChildren().clear();
    }

    @FXML
    protected void onRemoveButtonPressed(ActionEvent actionEvent){
        MusicBand musicBand =  (MusicBand) tableView.getSelectionModel().getSelectedItem();
        Notifications.create().text(musicBand.getName() + " Selected!").position(Pos.TOP_CENTER).show();
        modelsCollection.remove(musicBand);
    }

    @FXML
    protected void logOutPressed(ActionEvent actionEvent){
        try{
            Command command = new LogOutCommand(Invoker.getInstance());
            Invoker.getInstance().invokeCommand(command);
            Parent parent = FXMLLoader.load(AuthorizationFormController.class.getResource("AuthorizationForm.fxml"));
            Scene scene = new Scene(parent, 660, 400);
            MainApplication.getPrimaryStage().setScene(scene);
        }
        catch (IOException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can not load authorization form!");
            alert.show();
        }
    }

    public static MainFormController getMainFormController() {
        return mainFormController;
    }

    public static void updateCollectionList(ArrayList<MusicBand> newList){
        modelsCollection.clear();
        modelsCollection.addAll(newList);
    }

    public HBox getFiltersHBox(){
        return filtersHBox;
    }

    public TableView getTableView(){return tableView;}

    public Button getRemoveFiltersButton(){
        return removeFiltersButton;
    }

    public TableViewHandler getTableViewHandler(){
        return tableViewHandler;
    }

    public Menu getUserMenu(){
        return userMenu;
    }
}
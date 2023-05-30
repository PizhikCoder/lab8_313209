package client.UI.Controllers;

import client.MainApplication;
import client.UI.resourcebundles.enums.AvailableLocales;
import client.UI.resourcebundles.enums.CommandsAnswers;
import client.UI.resourcebundles.enums.MainFormElements;
import client.UI.resourcebundles.enums.RuntimeOutputs;
import client.backend.commands.*;
import client.backend.core.Invoker;
import client.backend.core.MusicBandFieldsValidators;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import org.controlsfx.control.Notifications;
import shared.commands.enums.DataField;
import shared.core.ClientInfo;
import shared.core.models.MusicBand;
import client.backend.tableHandlers.TableViewHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import shared.interfaces.IPrinter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class MainFormController {
    @FXML
    private Menu infoMenu;

    @FXML
    private Menu settingsMenu;

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem languageMenuItem;

    @FXML
    private MenuItem infoMenuItem;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private HBox filtersHBox;

    @FXML
    private TableView tableView;

    private volatile static ObservableList<MusicBand> modelsCollection = FXCollections.observableArrayList();

    @FXML
    private Button removeFiltersButton;

    @FXML
    private Button executeScriptButton;

    @FXML
    protected Button createFilterButton;

    @FXML
    protected Button addButton;

    @FXML
    protected Button addIfMinButton;

    @FXML
    protected Button updateButton;

    @FXML
    protected Button removeButton;

    @FXML
    protected Button removeByIdButton;

    @FXML
    protected Button clearButton;

    @FXML
    protected Button filterLessThanFrontManButton;

    @FXML
    protected Button countGreaterThanFrontManButton;

    @FXML
    protected Button groupCountingByCoordinatesButton;

    @FXML
    protected Label controllersLabel;

    @FXML
    private Menu userMenu;

    @FXML
    private Button visualizeButton;

    private static SimpleObjectProperty<AvailableLocales> currentLocale = new SimpleObjectProperty<>(AvailableLocales.ENGLISH);

    private final int VISUALIZATION_FORM_WIDTH = 800;

    private final int VISUALIZATION_FORM_HEIGHT = 600;

    private final int FILTER_CREATING_FROM_WIDTH = 400;

    private final int FILTER_CREATING_FORM_HEIGHT = 300;

    private final int ADDITIONAL_FORM_WIDTH = 300;

    private final int ADDITIONAL_FORM_HEIGHT = 200;

    private final int MUSIC_BAND_CREATING_AND_UPDATING_FORM_HEIGHT = 600;

    private final int MUSIC_BAND_CREATING_AND_UPDATING_FORM_WIDTH = 400;

    private final int LANGUAGE_CHANGING_FORM_WIDTH = 300;

    private final int LANGUAGE_CHANGING_FORM_HEIGHT = 200;


    private volatile TableViewHandler tableViewHandler;

    private static MainFormController mainFormController;


    @FXML
    public void initialize() {
        mainFormController = this;
        tableViewHandler = new TableViewHandler(tableView, modelsCollection);
        tableViewHandler.initializeColumns();
        currentLocale.addListener(change -> updateLocale());
        updateLocale();
    }

    private void updateLocale() {
        tableViewHandler.initializeColumns();

        removeFiltersButton.setText(MainFormElements.REMOVE_FILTERS_BUTTON.toString());
        createFilterButton.setText(MainFormElements.CREATE_FILTER_BUTTON.toString());
        addButton.setText(MainFormElements.ADD_BUTTON.toString());
        addIfMinButton.setText(MainFormElements.ADD_IF_MIN_BUTTON.toString());
        updateButton.setText(MainFormElements.UPDATE_BUTTON.toString());
        removeButton.setText(MainFormElements.REMOVE_BUTTON.toString());
        removeByIdButton.setText(MainFormElements.REMOVE_BY_ID_BUTTON.toString());
        clearButton.setText(MainFormElements.CLEAR_BUTTON.toString());
        filterLessThanFrontManButton.setText(MainFormElements.FILTER_LESS_THAN_FRONT_MAN_BUTTON.toString());
        countGreaterThanFrontManButton.setText(MainFormElements.COUNT_GREATER_THAN_FRONT_MAN_BUTTON.toString());
        groupCountingByCoordinatesButton.setText(MainFormElements.GROUP_COUNTING_BY_COORDINATES_BUTTON.toString());
        controllersLabel.setText(MainFormElements.CONTROLLERS_LABEL.toString());
        infoMenu.setText(MainFormElements.INFO_MENU.toString());
        helpMenu.setText(MainFormElements.HELP_MENU.toString());
        settingsMenu.setText(MainFormElements.SETTINGS_MENU.toString());
        logOutMenuItem.setText(MainFormElements.LOG_OUT_MENU_ITEM.toString());
        languageMenuItem.setText(MainFormElements.LANGUAGE_MENU_ITEM.toString());
        executeScriptButton.setText(MainFormElements.EXECUTE_SCRIPT_BUTTON.toString());
        visualizeButton.setText(MainFormElements.VISUALIZE_BUTTON.toString());
        infoMenuItem.setText(MainFormElements.INFO_MENU.toString());
    }

    @FXML
    protected void onAddButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            button.setDisable(true);
            MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = initCreatingForm();
            Map<DataField, Object> data = musicBandCreatingAndUpdatingFormController.getData();
            if (data == null) return;
            Command command = new AddCommand(data, Invoker.getInstance());
            Invoker.getInstance().invokeCommand(command);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        } finally {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onRemoveByIdButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            button.setDisable(true);
            AdditionalFormOfDataCollectionController additionalFormOfDataCollectionController = initAdditionalForm();
            String value = additionalFormOfDataCollectionController.getResult();
            prepareAndInvokeRemoveByIdCommand(value);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        } finally {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onClearButtonPressed(ActionEvent actionEvent) {
        Command command = new ClearCommand(Invoker.getInstance());
        Invoker.getInstance().invokeCommand(command);
    }

    private void prepareAndInvokeRemoveByIdCommand(String value) {
        if (MusicBandFieldsValidators.bandIdValidate(value)) {
            long id = Long.parseLong(value);
            if (modelsCollection.stream().map(MusicBand::getId).noneMatch(x -> x == id)) {
                Notifications.create().position(Pos.TOP_CENTER).text(CommandsAnswers.ID_DOES_NOT_EXIST.toString()).show();
                return;
            }
            Command command = new RemoveByIdCommand(Invoker.getInstance());
            Invoker.getInstance().invokeCommand(command, value);
        }
    }

    @FXML
    protected void onFilterCreatingButtonPressed(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FilterCreatorForm.fxml"));
            Parent node = fxmlLoader.load();
            FilterCreatorFormController filterCreatorFormController = fxmlLoader.getController();
            Scene scene = new Scene(node, FILTER_CREATING_FROM_WIDTH, FILTER_CREATING_FORM_HEIGHT);
            Stage stage = new Stage();
            stage.setResizable(false);
            filterCreatorFormController.setCurrentStage(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        }
    }

    @FXML
    protected void onFilterRemovingButtonPressed(ActionEvent actionEvent) {
        filtersHBox.getChildren().clear();
    }

    @FXML
    protected void onFilterLessThanFrontManButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            button.setDisable(true);
            AdditionalFormOfDataCollectionController additionalFormOfDataCollectionController = initAdditionalForm();
            String value = additionalFormOfDataCollectionController.getResult();
            if (!value.isBlank() && MusicBandFieldsValidators.personHeightCheck(value)) {
                value = value.replace(",", ".");
                Command command = new FilterLessThanFrontManCommand(filtersHBox);
                Invoker.getInstance().invokeCommand(command, value);
            } else {
                Notifications.create().text(CommandsAnswers.COUNT_GREATER_THAN_FRONT_MAN_NOT_EXECUTED.toString()).show();
            }
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        } finally {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onRemoveButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            button.setDisable(true);
            MusicBand musicBand = (MusicBand) tableView.getSelectionModel().getSelectedItem();
            if (musicBand != null) {
                if (!checkModelUserId(musicBand)) return;
                Command command = new RemoveByIdCommand(Invoker.getInstance());
                Invoker.getInstance().invokeCommand(command, String.valueOf(musicBand.getId()));
            } else {
                IPrinter printer = Invoker.getInstance().getPrinter();
                printer.print(RuntimeOutputs.MODEL_WAS_NOT_SELECTED_IN_TABLE.toString());
            }
        } finally {
            button.setDisable(false);
        }

    }

    @FXML
    protected void onCountGreaterThanFrontManButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            button.setDisable(true);
            AdditionalFormOfDataCollectionController additionalFormOfDataCollectionController = initAdditionalForm();
            String value = additionalFormOfDataCollectionController.getResult();
            Command command = new CountGreaterThanFrontManCommand(tableViewHandler.getSortedList().toArray(MusicBand[]::new));
            Invoker.getInstance().invokeCommand(command, value);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        } finally {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onExecuteScriptButtonPressed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null && file.exists()) {
            Command command = new ExecuteScriptCommand(Invoker.getInstance().getPrinter());
            Invoker.getInstance().invokeCommand(command, file.getAbsolutePath());
        }
    }

    @FXML
    protected void onAddIfMinButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            button.setDisable(true);
            AdditionalFormOfDataCollectionController additionalFormOfDataCollectionController = initAdditionalForm();
            String value = additionalFormOfDataCollectionController.getResult();
            prepareAndInvokeAddIfMinCommand(value);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        } finally {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onGroupCountingByCoordinatesButtonPressed(ActionEvent actionEvent) {
        Command command = new GroupCountingByCoordinatesCommand(MainFormController.getMainFormController().getTableViewHandler().getSortedList().toArray(MusicBand[]::new));
        Invoker.getInstance().invokeCommand(command);
    }

    @FXML
    protected void onVisualizationButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        Stage stage = new Stage();
        try {
            button.setDisable(true);
            FXMLLoader fxmlLoader = new FXMLLoader(VisualizerFormController.class.getResource("VisualizerForm.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent, VISUALIZATION_FORM_WIDTH, VISUALIZATION_FORM_HEIGHT);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            stage.close();
            ((VisualizerFormController) fxmlLoader.getController()).clearResources();
            System.gc();

        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        } finally {
            button.setDisable(false);
        }
    }


    private void prepareAndInvokeAddIfMinCommand(String value) {
        try {
            if (MusicBandFieldsValidators.bandIdValidate(value)) {
                long id = Long.parseLong(value);
                if (modelsCollection.stream().map(MusicBand::getId).anyMatch(x -> x <= id)) {
                    Notifications.create().position(Pos.TOP_CENTER).text(CommandsAnswers.ADD_IF_MIN_COMMAND_ID_IS_NOT_MIN.toString()).show();
                    return;
                }
                MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = initCreatingForm();
                Map<DataField, Object> data = musicBandCreatingAndUpdatingFormController.getData();
                Command command = new AddIfMinCommand(Invoker.getInstance(), id, data);
                Invoker.getInstance().invokeCommand(command, value);
            } else {
                Notifications.create().position(Pos.TOP_CENTER).text(CommandsAnswers.ADD_IF_MIN_COMMAND_ID_IN_WRONG_FORMAT.toString()).show();
            }
        } catch (IOException ioException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        }
    }

    private AdditionalFormOfDataCollectionController initAdditionalForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdditionalFormOfDataCollection.fxml"));
        Parent node = fxmlLoader.load();
        AdditionalFormOfDataCollectionController additionalFormOfDataCollectionController = fxmlLoader.getController();
        Scene scene = new Scene(node, ADDITIONAL_FORM_WIDTH, ADDITIONAL_FORM_HEIGHT);
        Stage stage = new Stage();
        additionalFormOfDataCollectionController.setCurrentStage(stage);
        stage.setScene(scene);
        stage.showAndWait();
        return additionalFormOfDataCollectionController;
    }

    @FXML
    protected void onLogOutPressed(ActionEvent actionEvent) {
        try {
            Command command = new LogOutCommand(Invoker.getInstance());
            Invoker.getInstance().invokeCommand(command);
            Parent parent = FXMLLoader.load(AuthorizationFormController.class.getResource("AuthorizationForm.fxml"));
            Scene scene = new Scene(parent, MainApplication.MAIN_SCENE_WIDTH, MainApplication.MAIN_SCENE_HEIGHT);
            MainApplication.getPrimaryStage().setScene(scene);
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        }
    }

    @FXML
    protected void onUpdateButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        try {
            MusicBand musicBand = (MusicBand) tableView.getSelectionModel().getSelectedItem();
            if (musicBand != null) {
                try {
                    if (!checkModelUserId(musicBand)) return;
                    MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = initCreatingForm();
                    Map<DataField, Object> data = musicBandCreatingAndUpdatingFormController.getData();
                    if (data == null) return;
                    Command command = new UpdateCommand(Invoker.getInstance(), musicBand.getId(), data);
                    Invoker.getInstance().invokeCommand(command);
                } catch (IOException exception) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
                    alert.show();
                }
            } else {
                IPrinter printer = Invoker.getInstance().getPrinter();
                printer.print(RuntimeOutputs.MODEL_WAS_NOT_SELECTED_IN_TABLE.toString());
            }
        } finally {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onLanguageMenuItemPressed(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LanguageChangingFormController.class.getResource("LanguageChangingForm.fxml"));
            Parent parent = fxmlLoader.load();
            LanguageChangingFormController languageChangingFormController = fxmlLoader.getController();
            Scene scene = new Scene(parent, LANGUAGE_CHANGING_FORM_WIDTH, LANGUAGE_CHANGING_FORM_HEIGHT);
            Stage stage = new Stage();
            languageChangingFormController.setCurrentStage(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.show();
        }
    }

    @FXML
    protected void onInfoMenuItemPressed(ActionEvent actionEvent) {
        Command command = new InfoCommand(modelsCollection);
        Invoker.getInstance().invokeCommand(command);
    }


    private MusicBandCreatingAndUpdatingFormController initCreatingForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicBandCreatingAndUpdatingFormController.class.getResource("MusicBandCreatingForm.fxml"));
        Parent node = fxmlLoader.load();
        Scene scene = new Scene(node, MUSIC_BAND_CREATING_AND_UPDATING_FORM_WIDTH, MUSIC_BAND_CREATING_AND_UPDATING_FORM_HEIGHT);
        Stage stage = new Stage();
        MusicBandCreatingAndUpdatingFormController musicBandCreatingAndUpdatingFormController = fxmlLoader.getController();
        musicBandCreatingAndUpdatingFormController.setCurrentStage(stage);
        stage.setScene(scene);
        stage.showAndWait();
        return musicBandCreatingAndUpdatingFormController;
    }

    private boolean checkModelUserId(MusicBand musicBand) {
        if (ClientInfo.getUserId() != musicBand.getOwnerId()) {
            Notifications.create().text(CommandsAnswers.UPDATE_COMMAND_NOT_EXECUTED.toString()).position(Pos.TOP_CENTER).show();
            return false;
        }
        return true;
    }

    public static MainFormController getMainFormController() {
        return mainFormController;
    }

    public static void updateCollectionList(ArrayList<MusicBand> newList) {
        modelsCollection.clear();
        modelsCollection.addAll(newList);
    }

    public HBox getFiltersHBox() {
        return filtersHBox;
    }

    public TableView getTableView() {
        return tableView;
    }

    public Button getRemoveFiltersButton() {
        return removeFiltersButton;
    }

    public TableViewHandler getTableViewHandler() {
        return tableViewHandler;
    }

    public Menu getUserMenu() {
        return userMenu;
    }

    public static SimpleObjectProperty<AvailableLocales> getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(AvailableLocales availableLocales) {
        currentLocale.set(availableLocales);
    }

    public ObservableList<MusicBand> getModelsCollection() {
        return modelsCollection;
    }
}
package client.UI.Controllers;

import client.UI.resourcebundles.enums.MusicBandCreatingAndUpdatingFormElements;
import client.UI.resourcebundles.enums.RuntimeOutputs;
import client.backend.commands.AddCommand;
import client.backend.commands.Command;
import client.backend.core.Invoker;
import client.backend.core.MusicBandFieldsValidators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import shared.commands.enums.DataField;
import shared.core.models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MusicBandCreatingAndUpdatingFormController {
    @FXML
    private TextField bandNameTextField;
    private boolean isBandNameValidity;
    @FXML
    private TextField coordinateXTextField;

    private boolean isCoordinateXValidity;
    @FXML
    private TextField coordinateYTextField;
    private boolean isCoordinateYValidity;
    @FXML
    private TextField numberOfParticipantsTextField;
    private boolean isNumberOfParticipantsValidity;
    @FXML
    private ComboBox<MusicGenre> genreComboBox;
    private boolean isGenreValidity;
    @FXML
    private TextField personNameTextField;
    private boolean isPersonNameValidity;
    @FXML
    private TextField personHeightTextField;
    private boolean isPersonHeightValidity;
    @FXML
    private ComboBox<Country> personNationalityComboBox;
    private boolean isPersonNationalityValidity;
    @FXML
    private TextField locationXTextField;
    private boolean isLocationXValidity;
    @FXML
    private TextField locationYTextField;
    private boolean isLocationYValidity;
    @FXML
    private TextField locationZTextField;

    private boolean isLocationZValidity;

    @FXML
    private Label bandNameLabel;

    @FXML
    private Label coordinateXLabel;

    @FXML
    private Label coordinateYLabel;

    @FXML
    private Label numberOfParticipantsLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label personNameLabel;

    @FXML
    private Label personHeightLabel;

    @FXML
    private Label personNationalityLabel;

    @FXML
    private Label locationXLabel;

    @FXML
    private Label locationYLabel;

    @FXML
    private Label locationZLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    private Map<DataField, Object> data;


    private Stage currentStage;
    @FXML
    public void initialize(){
        initListeners();
        preValidation();
        initGenreComboBox();
        initNationalityComboBox();
        updateLocale();
        MainFormController.getCurrentLocale().addListener(change->updateLocale());
    }

    private void updateLocale(){
        
        bandNameTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.BAND_NAME_TEXT_FIELD.toString());
        coordinateXTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.COORDINATE_X_TEXT_FIELD.toString());
        coordinateYTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.COORDINATE_Y_TEXT_FIELD.toString());
        numberOfParticipantsTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.NUMBER_OF_PARTICIPANTS_TEXT_FIELD.toString());
        genreComboBox.setPromptText(MusicBandCreatingAndUpdatingFormElements.GENRE_COMBO_BOX.toString());
        personNameTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.PERSON_NAME_TEXT_FIELD.toString());
        personHeightTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.PERSON_HEIGHT_TEXT_FIELD.toString());
        personNationalityComboBox.setPromptText(MusicBandCreatingAndUpdatingFormElements.PERSON_NATIONALITY_COMBO_BOX.toString());
        locationXTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.LOCATION_X_TEXT_FIELD.toString());
        locationYTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.LOCATION_Y_TEXT_FIELD.toString());
        locationZTextField.setPromptText(MusicBandCreatingAndUpdatingFormElements.LOCATION_Z_TEXT_FIELD.toString());

        bandNameLabel.setText(MusicBandCreatingAndUpdatingFormElements.BAND_NAME_LABEL.toString());
        coordinateXLabel.setText(MusicBandCreatingAndUpdatingFormElements.COORDINATE_X_LABEL.toString());
        coordinateYLabel.setText(MusicBandCreatingAndUpdatingFormElements.COORDINATE_Y_LABEL.toString());
        numberOfParticipantsLabel.setText(MusicBandCreatingAndUpdatingFormElements.NUMBER_OF_PARTICIPANTS_LABEL.toString());
        genreLabel.setText(MusicBandCreatingAndUpdatingFormElements.GENRE_LABEL.toString());
        personNameLabel.setText(MusicBandCreatingAndUpdatingFormElements.PERSON_NAME_LABEL.toString());
        personHeightLabel.setText(MusicBandCreatingAndUpdatingFormElements.PERSON_HEIGHT_LABEL.toString());
        personNationalityLabel.setText(MusicBandCreatingAndUpdatingFormElements.PERSON_NATIONALITY_LABEL.toString());
        locationXLabel.setText(MusicBandCreatingAndUpdatingFormElements.LOCATION_X_LABEL.toString());
        locationYLabel.setText(MusicBandCreatingAndUpdatingFormElements.LOCATION_Y_LABEL.toString());
        locationZLabel.setText(MusicBandCreatingAndUpdatingFormElements.LOCATION_Z_LABEL.toString());

        okButton.setText(MusicBandCreatingAndUpdatingFormElements.OK_Button.toString());
        cancelButton.setText(MusicBandCreatingAndUpdatingFormElements.CANCEL_BUTTON.toString());
    }

    private void preValidation(){
        MusicBandFieldsValidators.bandNameValidate(bandNameTextField, this);
        MusicBandFieldsValidators.coordinateXValidate(coordinateXTextField, this);
        MusicBandFieldsValidators.coordinateYValidate(coordinateYTextField, this);
        MusicBandFieldsValidators.numberOfParticipantsValidate(numberOfParticipantsTextField, this);
        MusicBandFieldsValidators.genreValidate(genreComboBox, this);
        MusicBandFieldsValidators.personNameValidate(personNameTextField, this);
        MusicBandFieldsValidators.personHeightValidate(personHeightTextField, this);
        MusicBandFieldsValidators.personNationalityValidate(personNationalityComboBox, this);
        MusicBandFieldsValidators.locationXValidate(locationXTextField, this);
        MusicBandFieldsValidators.locationYValidate(locationYTextField, this);
        MusicBandFieldsValidators.locationZValidate(locationZTextField, this);

    }

    private void initListeners(){
        bandNameTextField.textProperty().addListener(change-> MusicBandFieldsValidators.bandNameValidate(bandNameTextField, this));
        coordinateXTextField.textProperty().addListener(change->MusicBandFieldsValidators.coordinateXValidate(coordinateXTextField, this));
        coordinateYTextField.textProperty().addListener(change->MusicBandFieldsValidators.coordinateYValidate(coordinateYTextField, this));
        numberOfParticipantsTextField.textProperty().addListener(change -> MusicBandFieldsValidators.numberOfParticipantsValidate(numberOfParticipantsTextField, this));
        genreComboBox.valueProperty().addListener(change->MusicBandFieldsValidators.genreValidate(genreComboBox, this));
        personNameTextField.textProperty().addListener(change->MusicBandFieldsValidators.personNameValidate(personNameTextField, this));
        personHeightTextField.textProperty().addListener(change -> MusicBandFieldsValidators.personHeightValidate(personHeightTextField, this));
        personNationalityComboBox.valueProperty().addListener(change -> MusicBandFieldsValidators.personNationalityValidate(personNationalityComboBox, this));
        locationXTextField.textProperty().addListener(change->MusicBandFieldsValidators.locationXValidate(locationXTextField, this));
        locationYTextField.textProperty().addListener(change->MusicBandFieldsValidators.locationYValidate(locationYTextField, this));
        locationZTextField.textProperty().addListener(change->MusicBandFieldsValidators.locationZValidate(locationZTextField, this));

    }

    private void initGenreComboBox(){
        for (MusicGenre genre : MusicGenre.values()){
            genreComboBox.getItems().add(genre);
        }
    }

    private void initNationalityComboBox(){
        for (Country country : Country.values()){
            personNationalityComboBox.getItems().add(country);
        }
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

    @FXML
    protected void onOkButtonPressed(ActionEvent actionEvent){
        if (checkValidities()){
            data = collectData();
            currentStage.close();
            return;
        }
        Notifications.create().text(RuntimeOutputs.FIELDS_DOES_NOT_VALID.toString()).position(Pos.TOP_CENTER).show();
    }

    @FXML
    protected void onCloseButtonPressed(ActionEvent actionEvent){
        currentStage.close();
    }

    private Map<DataField, Object> collectData(){
        Map<DataField, Object> data = new HashMap();
        data.put(DataField.NAME, bandNameTextField.getText());
        int coordinateX = Integer.parseInt(coordinateXTextField.getText());
        double coordinateY = Double.parseDouble(coordinateYTextField.getText().replace(",", "."));
        Coordinates coordinates = new Coordinates(coordinateX,coordinateY);
        data.put(DataField.COORDINATES, coordinates);
        data.put(DataField.NUMBER_OF_PARTICIPANTS, Integer.parseInt(numberOfParticipantsTextField.getText()));
        data.put(DataField.GENRE, genreComboBox.getValue());

        String personName = personNameTextField.getText();
        Float personHeight = personHeightTextField.getText().isEmpty() ? null : Float.parseFloat(personHeightTextField.getText().replace(",", "."));
        Country nationality = personNationalityComboBox.getValue();
        int locationX = Integer.parseInt(locationXTextField.getText());
        float locationY = Float.parseFloat(locationYTextField.getText().replace(",", "."));
        float locationZ = Float.parseFloat(locationZTextField.getText().replace(",", "."));
        Person person = new Person(personName, personHeight, nationality, new Location(locationX, locationY, locationZ));

        data.put(DataField.FRONTMAN, person);
        return data;
    }

    private boolean checkValidities(){
        return isBandNameValidity && isCoordinateXValidity && isCoordinateYValidity
                && isGenreValidity && isLocationYValidity && isLocationXValidity
                && isLocationZValidity && isNumberOfParticipantsValidity && isPersonNameValidity
                && isPersonHeightValidity && isPersonNationalityValidity;
    }

    public void setBandNameValidity(boolean bandNameValidity) {
        isBandNameValidity = bandNameValidity;
    }

    public void setCoordinateXValidity(boolean coordinateXValidity) {
        isCoordinateXValidity = coordinateXValidity;
    }

    public void setCoordinateYValidity(boolean coordinateYValidity) {
        isCoordinateYValidity = coordinateYValidity;
    }

    public void setNumberOfParticipantsValidity(boolean numberOfParticipantsValidity) {
        isNumberOfParticipantsValidity = numberOfParticipantsValidity;
    }

    public void setGenreValidity(boolean genreValidity) {
        isGenreValidity = genreValidity;
    }

    public void setPersonNameValidity(boolean personNameValidity) {
        isPersonNameValidity = personNameValidity;
    }

    public void setPersonHeightValidity(boolean personHeightValidity) {
        isPersonHeightValidity = personHeightValidity;
    }

    public void setPersonNationalityValidity(boolean personNationalityValidity) {
        isPersonNationalityValidity = personNationalityValidity;
    }

    public void setLocationXValidity(boolean locationXValidity) {
        isLocationXValidity = locationXValidity;
    }

    public void setLocationYValidity(boolean locationYValidity) {
        isLocationYValidity = locationYValidity;
    }

    public void setLocationZValidity(boolean locationZValidity) {
        isLocationZValidity = locationZValidity;
    }

    public void setCurrentStage(Stage stage){
        this.currentStage = stage;
    }

    public Map<DataField, Object> getData(){
        return data;
    }
}

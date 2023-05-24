package client.backend.core;

import client.UI.Controllers.MusicBandCreatingAndUpdatingFormController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MusicBandFieldsValidators {
    public static boolean bandNameValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller) {
        String value = textField.getText();
        if (value.isBlank()) {
            setWrongValueTextFieldStyle(textField);
            controller.setBandNameValidity(false);
            return false;
        }
        setAcceptedValueTextFieldStyle(textField);
        controller.setBandNameValidity(true);
        return true;
    }

    public static boolean bandIdValidate(String value){
        try{
            long id  = Long.parseLong(value);
            if (id>0){
                return true;
            }
        }
        catch (NumberFormatException numberFormatException){}
        return false;
    }

    public static boolean coordinateXValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (!textField.getText().isBlank() && coordinateXCheck(textField.getText())){
            setAcceptedValueTextFieldStyle(textField);
            controller.setCoordinateXValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setCoordinateXValidity(false);
        return false;
    }

    public static boolean coordinateYValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (!textField.getText().isBlank() && coordinateYCheck(textField.getText())){
            setAcceptedValueTextFieldStyle(textField);
            controller.setCoordinateYValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setCoordinateYValidity(false);
        return false;
    }

    public static boolean numberOfParticipantsValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (!textField.getText().isBlank() && numberOfParticipantsCheck(textField.getText())){
            setAcceptedValueTextFieldStyle(textField);
            controller.setNumberOfParticipantsValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setNumberOfParticipantsValidity(false);
        return false;
    }

    public static boolean genreValidate(ComboBox comboBox, MusicBandCreatingAndUpdatingFormController controller){
        setAcceptedValueTextFieldStyle(comboBox);
        controller.setGenreValidity(true);
        return true;
    }


    public static boolean personHeightValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (personHeightCheck(textField.getText())){
            setAcceptedValueTextFieldStyle(textField);
            controller.setPersonHeightValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setPersonHeightValidity(false);
        return false;
    }

    public static boolean personNationalityValidate(ComboBox comboBox, MusicBandCreatingAndUpdatingFormController controller){
        if (comboBox.getValue() != null){
            setAcceptedValueTextFieldStyle(comboBox);
            controller.setPersonNationalityValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(comboBox);
        controller.setPersonNationalityValidity(false);
        return false;
    }

    public static boolean locationXValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (!textField.getText().isBlank() && locationXCheck(textField.getText())){
            setAcceptedValueTextFieldStyle(textField);
            controller.setLocationXValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setLocationXValidity(false);
        return false;
    }

    public static boolean locationYValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (!textField.getText().isBlank() && locationYCheck(textField.getText())){
            setAcceptedValueTextFieldStyle(textField);
            controller.setLocationYValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setLocationYValidity(false);
        return false;
    }

    public static boolean locationZValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (!textField.getText().isBlank() && locationZCheck(textField.getText())){
            setAcceptedValueTextFieldStyle(textField);
            controller.setLocationZValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setLocationZValidity(false);
        return false;
    }

    public static boolean personNameValidate(TextField textField, MusicBandCreatingAndUpdatingFormController controller){
        if (!textField.getText().isBlank()){
            setAcceptedValueTextFieldStyle(textField);
            controller.setPersonNameValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setPersonNameValidity(false);
        return false;
    }

    private static boolean coordinateXCheck(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean coordinateYCheck(String y) {
        y = y.replace(',', '.');
        try {
            return Double.parseDouble(y)<=742;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean numberOfParticipantsCheck(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public static boolean personHeightCheck(String height) {
        height = height.replace(',', '.');
        try {
            return  height.isEmpty() || Float.parseFloat(height) > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean locationXCheck(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean locationYCheck(String value) {
        value = value.replace(',', '.');
        try {
            return Float.parseFloat(value) > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean locationZCheck(String value) {
        value = value.replace(',', '.');
        try {
            return Float.parseFloat(value) > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static void setWrongValueTextFieldStyle(TextField textField){
        textField.setStyle("""
                    -fx-border-radius: 100;
                    -fx-background-radius: 100;
                    -fx-border-width: 2;
                    -fx-border-color: red;
                    """);
    }

    private static void setAcceptedValueTextFieldStyle(TextField textField){
        textField.setStyle("""
                    -fx-border-radius: 100;
                    -fx-background-radius: 100;
                    -fx-border-width: 2;
                    -fx-border-color: green;
                    """);
    }

    private static void setWrongValueTextFieldStyle(ComboBox comboBox){
        comboBox.setStyle("""
                    -fx-border-radius: 100;
                    -fx-background-radius: 100;
                    -fx-border-width: 2;
                    -fx-border-color: red;
                    """);
    }

    private static void setAcceptedValueTextFieldStyle(ComboBox comboBox){
        comboBox.setStyle("""
                    -fx-border-radius: 100;
                    -fx-background-radius: 100;
                    -fx-border-width: 2;
                    -fx-border-color: green;
                    """);
    }
}

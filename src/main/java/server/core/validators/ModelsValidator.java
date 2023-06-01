package server.core.validators;

import server.core.Invoker;
import shared.core.models.*;
import shared.interfaces.IPrinter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains full logic of model fields validating.
 */
public class ModelsValidator {

    private static final int Y_LIMIT = 3000;
    private static final int X_LIMIT = 3000;

    private static final Logger logger = Logger.getLogger(ModelsValidator.class.getName());

    private static ArrayList<Long> usedIDs;


    /**
     * Checks the array of models.
     * @param musicBands
     * @param invoker
     * @return
     */
    public static MusicBand[] modelsCheck(MusicBand[] musicBands, Invoker invoker){
        if (musicBands == null) return new MusicBand[0];
        usedIDs = invoker.getModelsManager().getUsedIDs();
        ArrayList<MusicBand> validatedModels = new ArrayList<>();
        for (MusicBand musicBand : musicBands){
            if (!modelCheck(musicBand)){
                logger.log(Level.WARNING, "Detected invalid model. Id: " + musicBand.getId());
            }
            else {
                validatedModels.add(musicBand);
            }
        }
        MusicBand[] toReturn = new MusicBand[validatedModels.size()];
        return validatedModels.toArray(toReturn);
    }


    /**
     * Checks the only one model.
     * @param musicBand
     * @return
     */
    public static Boolean modelCheck(MusicBand musicBand){


        return nameValueCheck(musicBand.getName())
                && coordinatesValueCheck(musicBand.getCoordinates())
                && numberOfParticipantsValueCheck(musicBand.getNumberOfParticipants())
                && frontManValueCheck(musicBand.getFrontMan());
    }

    /**
     * Checks id validity.
     * @param id input id.
     * @return validating result.
     */
    public static boolean idValueCheck(long id){
        if (id <= 0 || usedIDs.contains(id)){
            return false;
        }
        return true;
    }

    public static boolean idExist(long id){
        return usedIDs.contains(id);
    }

    /**
     * Checks coordinates validity.
     * @param coordinates
     * @return validating result.
     */
    public static boolean coordinatesValueCheck(Coordinates coordinates){
        if (!coordinateYCheck(coordinates.getY()) || !coordinateXCheck(coordinates.getX())){
            return false;
        }
        return true;
    }

    /**
     * Checks person validity.
     * @param frontMan
     * @return validating result.
     */
    public static boolean frontManValueCheck(Person frontMan){
        return nameValueCheck(frontMan.getName()) && personHeightValueCheck(frontMan.getHeight()) && nationalityValueCheck(frontMan.getNationality());
    }


    /**
     * Checks coordinate y validity.
     * @param y
     * @return validating result.
     */
    public static boolean coordinateYCheck(double y){
        return !(y>Y_LIMIT || y<0);
    }

    /**
     * Checks coordinate x validity.
     * @param x
     * @return validating result.
     */
    public static boolean coordinateXCheck(double x){
        return !(x>X_LIMIT || x<0);
    }

    /**
     * Checks name validity.
     * @param name
     * @return validating result.
     */
    public static boolean nameValueCheck(String name){
        return !(name == null || name.isBlank());
    }

    /**
     * Checks numberOfParticipants validity.
     * @param numberOfParticipants
     * @return validating result.
     */
    public static boolean numberOfParticipantsValueCheck(int numberOfParticipants){
        return !(numberOfParticipants<=0);
    }

    /**
     * Checks person's height validity.
     * @param height
     * @return validating result.
     */
    public static boolean personHeightValueCheck(Float height){
        return !(height != null && height<=0);
    }

    /**
     * Checks nationality validity.
     * @param country
     * @return validating result.
     */

    public static boolean nationalityValueCheck(Country country){
        return !(country == null);
    }


    /**
     * Checks a field on the null value.
     * @param element
     * @return
     * @param <T>
     */
    public static <T> String fastNullCheck(T element){
        if (element == null){
            return "";
        }
        else {
            return element.toString();
        }
    }
}

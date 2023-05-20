package shared.core.models;

import server.core.Invoker;
import shared.core.exceptions.FieldValueIsNotCorrectException;
import server.core.validators.ModelsValidator;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Model's class-constructor.
 */
public class MusicBand implements Serializable {
    private int ownerId;
    private long id;//Field must be unique, >0 and generated automatically
    private String name;//Can not be empty
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private int numberOfParticipants;//Must be >0
    private MusicGenre genre;//Can be null
    private Person frontMan;

    public MusicBand(int ownerId, long id, String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Person frontMan){
        setOwnerID(ownerId);
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(ZonedDateTime.now());
        setNumberOfParticipants(numberOfParticipants);
        setGenre(genre);
        setFrontMan(frontMan);
    }

    public MusicBand(int ownerId, long id, ZonedDateTime creationDate,String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Person frontMan){
        setOwnerID(ownerId);
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(creationDate);
        setNumberOfParticipants(numberOfParticipants);
        setGenre(genre);
        setFrontMan(frontMan);
    }

    public MusicBand(int ownerId, String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Person frontMan){
        setOwnerID(ownerId);
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(ZonedDateTime.now());
        setNumberOfParticipants(numberOfParticipants);
        setGenre(genre);
        setFrontMan(frontMan);
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Person getFrontMan() {
        return frontMan;
    }

    public int getOwnerId(){
        return ownerId;
    }

    public void setId(long id) {
        if (id>0 || Invoker.getIsDataLoading()){
            this.id = id;
        }
        else{
            throw new FieldValueIsNotCorrectException();
        }
    }

    public void setName(String name) {
        if (!name.isBlank() || Invoker.getIsDataLoading()){
            this.name = name;
        }
        else {
            throw new FieldValueIsNotCorrectException();
        }
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null || Invoker.getIsDataLoading()){
            this.coordinates = coordinates;
        }
        else {
            throw new FieldValueIsNotCorrectException();
        }
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        if (creationDate!=null || Invoker.getIsDataLoading()){
            this.creationDate = creationDate;
        }
        else {
            throw new FieldValueIsNotCorrectException();
        }
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        if (numberOfParticipants > 0 || Invoker.getIsDataLoading()){
            this.numberOfParticipants = numberOfParticipants;
        }
        else {
            throw new FieldValueIsNotCorrectException();
        }
    }

    private void setOwnerID(int ownerId){
        this.ownerId = ownerId;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setFrontMan(Person frontMan) {
        if (frontMan != null || Invoker.getIsDataLoading()){
            this.frontMan = frontMan;
        }
        else {
            throw new FieldValueIsNotCorrectException();
        }
    }

    @Override
    public String toString() {
        return String.format("\nOwner: %s\nID: %s\nName: %s\nCoordinates: %s\nCreation date: %s\nNumber of participants: %s\nGenre: %s\nFront man: \n%s", ownerId, id,name,coordinates,creationDate,numberOfParticipants, ModelsValidator.fastNullCheck(genre).toLowerCase(),frontMan);
    }
}

package server.core.managers;

import server.core.validators.ModelsValidator;
import shared.commands.enums.DataField;
import server.core.comparators.ModelsDefaultComparator;
import shared.core.models.*;
import shared.interfaces.IPrinter;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Contains tools to manage your collection.
 */
public class ModelsManager {
    private static final Logger logger = Logger.getLogger(ModelsManager.class.getName());
    private ArrayList<Long> usedIDs;
    private ArrayDeque<MusicBand> models;
    private String creationDate;
    private volatile ReentrantLock locker;


    public ModelsManager(ArrayDeque<MusicBand> models) {
        this.models = models;
        getModelsIDs();
        creationDate = ZonedDateTime.now().toLocalDate().toString();
        locker = new ReentrantLock();
    }

    public MusicBand createModel(Map<DataField, Object> data) {
        locker.lock();
        try {
            return new MusicBand(
                    (int) data.get(DataField.OWNER_ID),
                    (String) data.get(DataField.NAME),
                    (Coordinates) data.get(DataField.COORDINATES),
                    (int) data.get(DataField.NUMBER_OF_PARTICIPANTS),
                    (MusicGenre) data.get(DataField.GENRE),
                    (Person) data.get(DataField.FRONTMAN)
            );
        } finally {
            locker.unlock();
        }
    }

    /**
     * Creates new model with custom ID.
     *
     * @param data data Map for model's constructor.
     * @param id   Desired id for the model.
     * @return new model.
     */
    public MusicBand createModel(Map<DataField, Object> data, long id, ZonedDateTime creationDate) {
        locker.lock();
        try {
            return new MusicBand(
                    (Integer) data.get(DataField.OWNER_ID),
                    id,
                    creationDate,
                    (String) data.get(DataField.NAME),
                    (Coordinates) data.get(DataField.COORDINATES),
                    (int) data.get(DataField.NUMBER_OF_PARTICIPANTS),
                    (MusicGenre) data.get(DataField.GENRE),
                    (Person) data.get(DataField.FRONTMAN)
            );
        } finally {
            locker.unlock();
        }
    }

    public MusicBand createModel(Map<DataField, Object> data, long id) {
        locker.lock();
        try {
            return new MusicBand(
                    (Integer) data.get(DataField.OWNER_ID),
                    id,
                    (String) data.get(DataField.NAME),
                    (Coordinates) data.get(DataField.COORDINATES),
                    (int) data.get(DataField.NUMBER_OF_PARTICIPANTS),
                    (MusicGenre) data.get(DataField.GENRE),
                    (Person) data.get(DataField.FRONTMAN)
            );
        } finally {
            locker.unlock();
        }
    }

    /**
     * Add models ArrayDeque to the collection.
     *
     * @param queue Models collection.
     */
    public void addModels(ArrayDeque<MusicBand> queue) {
        locker.lock();
        try {
            models.addAll(queue);
            queue.stream().map(MusicBand::getId).forEach(usedIDs::add);
            sort();
        } finally {
            locker.unlock();
        }
    }

    /**
     * Add model to the collection.
     *
     * @param model Model object.
     */
    public boolean addModels(MusicBand model) {
        locker.lock();
        try {
            if (ModelsValidator.modelCheck(model)) {
                models.add(model);
                usedIDs.add(model.getId());
                sort();
                return true;
            }
            return false;
        } finally {
            locker.unlock();
        }
    }

    /**
     * Get model from the collection by ID and recreate it.
     *
     * @param id   Model id.
     * @param data new model data.
     */
    public void updateModel(long id, Map<DataField, Object> data, IPrinter printer) {
        locker.lock();
        try {
            logger.log(Level.INFO, "Updating model.");
            MusicBand model = findModelById(id, printer);
            model.setName((String) data.get(DataField.NAME));
            model.setCoordinates((Coordinates) data.get(DataField.COORDINATES));
            model.setNumberOfParticipants((int) data.get(DataField.NUMBER_OF_PARTICIPANTS));
            model.setGenre((MusicGenre) data.get(DataField.GENRE));
            model.setFrontMan((Person) data.get(DataField.FRONTMAN));
        } finally {
            locker.unlock();
        }
    }

    /**
     * Find model in the collection by id.
     *
     * @param id model id.
     * @return object of model or null if not exist or collection is empty.
     */
    public MusicBand findModelById(Long id, IPrinter printer) {
        locker.lock();
        try {
            if (models.isEmpty()) {
                printer.print("Collection is empty!");
                return null;
            }
            MusicBand[] acceptedModels = models.stream().filter(x -> x.getId() == id).toArray(MusicBand[]::new);
            if (acceptedModels.length == 0) {
                printer.print("Can not find element with this id.");
                return null;
            }
            return acceptedModels[0];
        } finally {
            locker.unlock();
        }
    }

    /**
     * remove all elements from the collection.
     */
    public void removeAll(IPrinter printer) {
        try {
            locker.lock();
            models.stream().forEach(models::remove);
            usedIDs.clear();
        } finally {
            locker.unlock();
        }
    }

    /**
     * Makes the default sorting.
     */
    public void sort() {
        locker.lock();
        try {
            models = models.stream().sorted(new ModelsDefaultComparator()).collect(Collectors.toCollection(ArrayDeque<MusicBand>::new));
        } finally {
            locker.unlock();
        }
    }

    /**
     * Remove model from the collection by model id.
     *
     * @param id model id.
     */
    public void removeById(long id, IPrinter printer) {
        try {
            locker.lock();
            MusicBand musicBand = findModelById(id, printer);
            if (musicBand != null) {
                logger.log(Level.INFO, "Removing model.");
                models.remove(musicBand);
                usedIDs.remove(id);

                printer.print(String.format("Model %s successfully removed.", id));
            } else printer.print("Model does not exist!");

        } finally {
            locker.unlock();
        }
    }


    public ArrayDeque<MusicBand> getModels() {
        return models;
    }

    public ArrayList<Long> getUsedIDs() {
        return usedIDs;
    }

    public String getCreationDate() {
        return creationDate;
    }

    private void getModelsIDs() {
        usedIDs = new ArrayList<>();
        for (MusicBand musicBand : models) {
            usedIDs.add(musicBand.getId());
        }
    }
}

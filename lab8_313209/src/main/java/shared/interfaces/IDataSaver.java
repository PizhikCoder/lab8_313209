package shared.interfaces;

import shared.core.models.MusicBand;

/**
 * Contains logic-declaration for saving models.
 */
public interface IDataSaver {
    /**
     *
     * @param data models to save.
     * @return
     */
    boolean save(MusicBand[] data);
}

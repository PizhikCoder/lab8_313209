package shared.interfaces;

import shared.core.models.MusicBand;

/**
 * Contains logic-declaration for loading models.
 */
public interface IDataLoader {
    /**
     * loads data
     * @return
     */
    MusicBand[] load();
}

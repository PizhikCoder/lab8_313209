package shared.connection.interfaces;

import shared.core.models.MusicBand;

public interface IServerBackRequest extends IRequest {
    MusicBand getData();
}

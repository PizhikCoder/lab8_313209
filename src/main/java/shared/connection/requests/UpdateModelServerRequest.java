package shared.connection.requests;

import shared.connection.interfaces.IRequest;
import shared.connection.interfaces.IServerBackRequest;
import shared.core.models.MusicBand;

public class UpdateModelServerRequest implements IServerBackRequest {
    private MusicBand data;

    public UpdateModelServerRequest(MusicBand data){
        this.data = data;
    }

    @Override
    public MusicBand getData() {
        return data;
    }
}

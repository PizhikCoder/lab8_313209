package shared.connection.requests;

import shared.connection.interfaces.IServerBackRequest;
import shared.core.models.MusicBand;

public class RemoveModelServerRequest implements IServerBackRequest {
    private MusicBand data;

    public RemoveModelServerRequest(MusicBand data){
        this.data = data;
    }

    @Override
    public MusicBand getData() {
        return data;
    }
}

package shared.connection.requests;

import shared.connection.interfaces.IServerBackRequest;
import shared.core.models.MusicBand;

public class AddModelServerRequest implements IServerBackRequest {
    private MusicBand data;

    public AddModelServerRequest(MusicBand data){
        this.data = data;
    }

    @Override
    public MusicBand getData() {
        return data;
    }
}

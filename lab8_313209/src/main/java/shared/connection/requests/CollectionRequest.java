package shared.connection.requests;

import shared.connection.interfaces.IRequest;
import shared.core.models.MusicBand;

import java.util.ArrayList;

public class CollectionRequest implements IRequest {
    private ArrayList<MusicBand> data;

    public CollectionRequest(ArrayList<MusicBand> data){
        this.data = data;
    }

    @Override
    public ArrayList<MusicBand> getData() {
        return data;
    }
}

package server.database.interfaces;

import shared.commands.enums.DataField;
import shared.core.models.MusicBand;

import java.util.Map;

public interface IDatabaseHandlerDAO {
    int sendModel(MusicBand model);

    boolean updateModel(Map<DataField, Object> data, long modelId);

    boolean removeModel(long model_id);

    int sendModel(MusicBand model, long modelId);

    int getClientID(String login, String password);

    boolean clientExist(String login);

    void addClient(String login, String password);

    void clearModels();
}

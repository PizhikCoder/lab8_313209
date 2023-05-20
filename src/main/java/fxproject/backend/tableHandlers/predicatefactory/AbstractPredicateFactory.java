package fxproject.backend.tableHandlers.predicatefactory;

import fxproject.backend.tableHandlers.ColumnNames;
import fxproject.backend.tableHandlers.predicatefactory.implementations.*;

public class AbstractPredicateFactory {
    public PredicateFactory createFactory(ColumnNames name){
        return switch (name){
            case ID_COLUMN -> new IdColumnPredicateFactory();
            case NAME_COLUMN -> new NameColumnPredicateFactory();
            case OWNER_ID_COLUMN -> new OwnerIdColumnPredicateFactory();
            case COORDINATES_X_COLUMN -> new CoordinateXColumnPredicateFactory();
            case COORDINATES_Y_COLUMN -> new CoordinateYColumnPredicateFactory();
            case CREATION_DATE_COLUMN -> new CreationDateColumnPredicateFactory();
            case NUMBER_OF_PARTICIPANTS_COLUMN -> new NumberOfParticipantsColumnPredicateFactory();
            case GENRE_COLUMN -> new GenreColumnPredicateFactory();
            case PERSON_NAME_COLUMN -> new PersonNameColumnPredicateFactory();
            case PERSON_HEIGHT_COLUMN -> new PersonHeightColumnPredicateFactory();
            case PERSON_NATIONALITY_COLUMN -> new PersonNationalityColumnPredicateFactory();
            case LOCATION_X_COORDINATE -> new LocationXColumnPredicateFactory();
            case LOCATION_Y_COORDINATE -> new LocationYColumnPredicateFactory();
            case LOCATION_Z_COORDINATE -> new LocationZColumnPredicateFactory();
        };
    }
}

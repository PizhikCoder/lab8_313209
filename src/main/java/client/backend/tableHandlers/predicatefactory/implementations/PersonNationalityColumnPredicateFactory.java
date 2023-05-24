package client.backend.tableHandlers.predicatefactory.implementations;

import client.backend.tableHandlers.predicatefactory.FilterSigns;
import client.backend.tableHandlers.predicatefactory.PredicateFactory;
import shared.core.models.MusicBand;

import java.util.function.Predicate;

public class PersonNationalityColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<MusicBand> createPredicate(FilterSigns filterSign, String value) {
        return switch (filterSign){
            case MORE_THAN -> createMorePredicate(value);
            case EQUALITY -> createEqualPredicate(value);
            case INEQUALITY -> createInequalPredicate(value);
            case LESS_THAN -> createLessPredicate(value);
        };
    }

    private Predicate<MusicBand> createMorePredicate(String  value){
        return (band) -> band.getFrontMan().getNationality().toString().compareTo(value)>0;
    }

    private Predicate<MusicBand> createLessPredicate(String value){
        return (band) -> band.getFrontMan().getNationality().toString().compareTo(value)<0;
    }

    private Predicate<MusicBand> createEqualPredicate(String value){
        return (band) -> band.getFrontMan().getNationality().toString().equalsIgnoreCase(value);
    }

    private Predicate<MusicBand> createInequalPredicate(String value){
        return (band) -> !band.getFrontMan().getNationality().toString().equalsIgnoreCase(value);
    }
}

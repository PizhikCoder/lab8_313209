package client.backend.tableHandlers.predicatefactory.implementations;

import client.backend.tableHandlers.predicatefactory.FilterSigns;
import client.backend.tableHandlers.predicatefactory.PredicateFactory;
import shared.core.models.MusicBand;

import java.util.Objects;
import java.util.function.Predicate;

public class IdColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<MusicBand> createPredicate(FilterSigns filterSign, String value) {
        try{
            return switch (filterSign){
                case MORE_THAN -> createMorePredicate(Long.parseLong(value));
                case EQUALITY -> createEqualPredicate(Long.parseLong(value));
                case INEQUALITY -> createInequalPredicate(Long.parseLong(value));
                case LESS_THAN -> createLessPredicate(Long.parseLong(value));
            };
        }
        catch (NumberFormatException numberFormatException){
            return value == null ? Objects::isNull : x->false;
        }
    }

    private Predicate<MusicBand> createMorePredicate(Long value){
        return (band) -> band.getId() > value;
    }

    private Predicate<MusicBand> createLessPredicate(Long value){
        return (band) -> band.getId() < value;
    }

    private Predicate<MusicBand> createEqualPredicate(Long value){
        return (band) -> band.getId() == value;
    }

    private Predicate<MusicBand> createInequalPredicate(Long value){
        return (band) -> band.getId() != value;
    }
}

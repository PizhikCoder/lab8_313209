package fxproject.backend.tableHandlers.predicatefactory.implementations;

import fxproject.backend.tableHandlers.predicatefactory.FilterSigns;
import fxproject.backend.tableHandlers.predicatefactory.PredicateFactory;
import shared.core.models.MusicBand;

import java.util.Objects;
import java.util.function.Predicate;

public class CoordinateYColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<MusicBand> createPredicate(FilterSigns filterSign, String value) {
        try{
            return switch (filterSign){
                case MORE_THAN -> createMorePredicate(Double.parseDouble(value));
                case EQUALITY -> createEqualPredicate(Double.parseDouble(value));
                case INEQUALITY -> createInequalPredicate(Double.parseDouble(value));
                case LESS_THAN -> createLessPredicate(Double.parseDouble(value));
            };
        }
        catch (NumberFormatException numberFormatException){
            return value == null ? Objects::isNull : x->false;
        }
    }

    private Predicate<MusicBand> createMorePredicate(double value){
        return (band) -> band.getCoordinates().getY() > value;
    }

    private Predicate<MusicBand> createLessPredicate(double value){
        return (band) -> band.getCoordinates().getY() < value;
    }

    private Predicate<MusicBand> createEqualPredicate(double value){
        return (band) -> band.getCoordinates().getY() == value;
    }

    private Predicate<MusicBand> createInequalPredicate(double value){
        return (band) -> band.getCoordinates().getY()!=value;
    }
}

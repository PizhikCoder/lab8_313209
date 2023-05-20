package fxproject.backend.tableHandlers.predicatefactory.implementations;

import fxproject.backend.tableHandlers.predicatefactory.FilterSigns;
import fxproject.backend.tableHandlers.predicatefactory.PredicateFactory;
import shared.core.models.MusicBand;

import java.util.Objects;
import java.util.function.Predicate;

public class PersonHeightColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<MusicBand> createPredicate(FilterSigns filterSign, String value) {
        try{
            return switch (filterSign){
                case MORE_THAN -> createMorePredicate(Integer.parseInt(value));
                case EQUALITY -> createEqualPredicate(Integer.parseInt(value));
                case INEQUALITY -> createInequalPredicate(Integer.parseInt(value));
                case LESS_THAN -> createLessPredicate(Integer.parseInt(value));
            };
        }
        catch (NumberFormatException numberFormatException){
            return value == null ? Objects::isNull : x->false;
        }
    }

    private Predicate<MusicBand> createMorePredicate(int value){
        return (band) -> band.getFrontMan().getHeight() > value;
    }

    private Predicate<MusicBand> createLessPredicate(int value){
        return (band) -> band.getFrontMan().getHeight() < value;
    }

    private Predicate<MusicBand> createEqualPredicate(int value){
        return (band) -> band.getFrontMan().getHeight() == value;
    }

    private Predicate<MusicBand> createInequalPredicate(int value){
        return (band) -> band.getFrontMan().getHeight()!=value;
    }
}

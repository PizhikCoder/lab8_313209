package client.backend.tableHandlers.predicatefactory.implementations;

import client.backend.tableHandlers.predicatefactory.FilterSigns;
import client.backend.tableHandlers.predicatefactory.PredicateFactory;
import shared.core.models.MusicBand;

import java.util.Objects;
import java.util.function.Predicate;

public class PersonHeightColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<MusicBand> createPredicate(FilterSigns filterSign, String value) {
        try {
            return switch (filterSign) {
                case MORE_THAN -> createMorePredicate(Float.parseFloat(value));
                case EQUALITY -> createEqualPredicate(Float.parseFloat(value));
                case INEQUALITY -> createInequalPredicate(Float.parseFloat(value));
                case LESS_THAN -> createLessPredicate(Float.parseFloat(value));
            };
        } catch (NumberFormatException numberFormatException) {
            return value.isBlank() ? Objects::isNull : x -> false;
        }
    }

    private Predicate<MusicBand> createMorePredicate(float value) {
        return (band) -> band.getFrontMan().getHeight() != null && band.getFrontMan().getHeight() > value;
    }

    private Predicate<MusicBand> createLessPredicate(float value) {
        return (band) -> band.getFrontMan().getHeight() != null && band.getFrontMan().getHeight() < value;
    }

    private Predicate<MusicBand> createEqualPredicate(float value) {
        return (band) -> band.getFrontMan().getHeight() != null && band.getFrontMan().getHeight() == value;
    }

    private Predicate<MusicBand> createInequalPredicate(float value) {
        return (band) -> band.getFrontMan().getHeight() != null && band.getFrontMan().getHeight() != value;
    }
}

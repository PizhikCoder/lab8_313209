package fxproject.backend.tableHandlers.predicatefactory.implementations;

import fxproject.backend.tableHandlers.predicatefactory.FilterSigns;
import fxproject.backend.tableHandlers.predicatefactory.PredicateFactory;
import shared.core.models.MusicBand;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.function.Predicate;

public class CreationDateColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<MusicBand> createPredicate(FilterSigns filterSign, String value) {
        try {
            return switch (filterSign){
                case MORE_THAN -> createMorePredicate(LocalDate.parse(value));
                case EQUALITY -> createEqualPredicate(LocalDate.parse(value));
                case INEQUALITY -> createInequalPredicate(LocalDate.parse(value));
                case LESS_THAN -> createLessPredicate(LocalDate.parse(value));
            };
        }
        catch (DateTimeParseException dateTimeParseException){
            return value == null ? Objects::isNull : x->false;
        }
    }

    private Predicate<MusicBand> createMorePredicate(LocalDate value){
        return (band) -> band.getCreationDate().toLocalDate().isAfter(value);
    }

    private Predicate<MusicBand> createLessPredicate(LocalDate value){
        return (band) -> band.getCreationDate().toLocalDate().isBefore(value);
    }

    private Predicate<MusicBand> createEqualPredicate(LocalDate value){
        return (band) -> band.getCreationDate().toLocalDate().equals(value);
    }

    private Predicate<MusicBand> createInequalPredicate(LocalDate value){
        return (band) -> !band.getCreationDate().toLocalDate().equals(value);
    }
}

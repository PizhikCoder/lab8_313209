package client.backend.tableHandlers.predicatefactory;

import shared.core.models.MusicBand;
import java.util.function.Predicate;

public abstract class PredicateFactory {
    public abstract Predicate<MusicBand> createPredicate(FilterSigns filterSign, String value);
}

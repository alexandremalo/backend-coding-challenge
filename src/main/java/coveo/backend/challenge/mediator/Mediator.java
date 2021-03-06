package coveo.backend.challenge.mediator;

import coveo.backend.challenge.model.SuggestionResponse;

public interface Mediator {
    SuggestionResponse getSuggestions(String query, String longitude, String latitude);
    void clearCache();
}

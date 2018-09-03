package coveo.backend.challenge.rest;

import coveo.backend.challenge.model.Suggestion;
import coveo.backend.challenge.model.SuggestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Component
public class SuggestionRest {

    private final String SUGGESTION_URL = "/suggestions";

    @RequestMapping(value = SUGGESTION_URL, method = RequestMethod.GET)
    public ResponseEntity test(){
        SuggestionResponse suggestionResponse = new SuggestionResponse();
        Suggestion suggestion = new Suggestion();
        suggestion.setName("test1");
        suggestion.setLatitude("-13.3213");
        suggestion.setLongitude("52.1233");
        suggestion.setScore(new BigDecimal(0.8));
        suggestionResponse.addSuggestionsItem(suggestion);
        return ResponseEntity.ok(suggestionResponse);
    }
}

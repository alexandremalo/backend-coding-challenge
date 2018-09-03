package coveo.backend.challenge.rest;

import coveo.backend.challenge.mediator.Mediator;
import coveo.backend.challenge.model.Suggestion;
import coveo.backend.challenge.model.SuggestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Component
public class SuggestionREST {

    private final String SUGGESTION_URL = "/suggestions";
    private final String CLEAR_CACHE_URL = "/_clear-cache";

    private Mediator mediator;

    @Autowired
    public SuggestionREST(Mediator mediator){
        this.mediator = mediator;
    }

    @RequestMapping(value = SUGGESTION_URL, method = RequestMethod.GET)
    public ResponseEntity getSuggestions(){
        return ResponseEntity.ok(mediator.getSuggestions("test"));
    }

    //Clearing cache every 24 hours
    @Scheduled(fixedDelay = 86400000)
    @RequestMapping(value = CLEAR_CACHE_URL, method = RequestMethod.GET)
    public ResponseEntity clearCache(){
        mediator.clearCache();
        return ResponseEntity.noContent().build();
    }


}

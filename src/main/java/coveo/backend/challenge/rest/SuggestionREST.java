package coveo.backend.challenge.rest;

import coveo.backend.challenge.mediator.Mediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity getSuggestions(@RequestParam(value = "q", required = false, defaultValue = "") String query,
                                         @RequestParam(value = "longitude", required = false, defaultValue = "") String longitude,
                                         @RequestParam(value = "latitude", required = false, defaultValue = "") String latitude){
        return ResponseEntity.ok(mediator.getSuggestions(query, longitude, latitude));
    }

    //Clearing cache every 24 hours
    @Scheduled(fixedDelayString = "${cities.refresh.rate}")
    @RequestMapping(value = CLEAR_CACHE_URL, method = RequestMethod.POST)
    public ResponseEntity clearCache() {
        mediator.clearCache();
        return ResponseEntity.noContent().build();
    }


}

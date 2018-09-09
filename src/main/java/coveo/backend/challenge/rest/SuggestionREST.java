package coveo.backend.challenge.rest;

import coveo.backend.challenge.mediator.Mediator;
import coveo.backend.challenge.model.SuggestionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "SuggestionController", description = "Expose Auto-complete API of North America large cities")
public class SuggestionREST {

    private final String SUGGESTION_URL = "/suggestions";
    private final String CLEAR_CACHE_URL = "/_clear-cache";

    private Mediator mediator;

    @Autowired
    public SuggestionREST(Mediator mediator){
        this.mediator = mediator;
    }


    @RequestMapping(value = SUGGESTION_URL, method = RequestMethod.GET)
    @ApiOperation(value = "Get city suggestions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned sorted list of large cities"),
            @ApiResponse(code = 400, message = "Invalid parameters"),
            @ApiResponse(code = 500, message = "Internal error during query")
    })
    public ResponseEntity<SuggestionResponse> getSuggestions(@RequestParam(value = "q", required = false, defaultValue = "") String query,
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

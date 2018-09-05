package coveo.backend.challenge.mediator;

import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.Suggestion;
import coveo.backend.challenge.model.SuggestionResponse;
import coveo.backend.challenge.service.CityScoreService;
import coveo.backend.challenge.service.CityService;
import coveo.backend.challenge.util.CityFinder;
import coveo.backend.challenge.util.SuggestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MediatorImpl implements Mediator {

    private CityService cityService;
    private CityScoreService cityScoreService;

    @Autowired
    public MediatorImpl(CityService cityService, CityScoreService cityScoreService){
        this.cityService = cityService;
        this.cityScoreService = cityScoreService;
    }

    @Override
    public SuggestionResponse getSuggestions(String query, String longitude, String latitude) {
        Map<Long, CityInfo> allCitiesMap = cityService.getAllCities();
        CityFinder cityFinder = new CityFinder();
        List<CityInfo> relevantCities = cityFinder.getRelevantCity(query, allCitiesMap);
        List<CityInfo> scoredAndSortedCities = cityScoreService.scoreAndSortCities(relevantCities, 0d, 0d);
        SuggestionResponse suggestionResponse = new SuggestionResponse();
        suggestionResponse.setSuggestions(new ArrayList<Suggestion>());
        for (CityInfo cityInfo: scoredAndSortedCities) {
            suggestionResponse.addSuggestionsItem(SuggestionMapper.fromCityInfo(cityInfo));
        }
        return suggestionResponse;
    }

    @Override
    public void clearCache() {
        cityService.clearCache();
    }
}

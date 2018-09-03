package coveo.backend.challenge.mediator;

import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.Suggestion;
import coveo.backend.challenge.model.SuggestionResponse;
import coveo.backend.challenge.service.CityService;
import coveo.backend.challenge.util.CityFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MediatorImpl implements Mediator {

    private CityService cityService;

    @Autowired
    public MediatorImpl(CityService cityService){
        this.cityService = cityService;
    }

    @Override
    public SuggestionResponse getSuggestions(String query) {
        Map<Long, CityInfo> allCitiesMap = cityService.getAllCities();
        CityFinder cityFinder = new CityFinder();
        List<CityInfo> relevantCities = cityFinder.getRelevantCity(query, allCitiesMap);

        SuggestionResponse suggestionResponse = new SuggestionResponse();
        suggestionResponse.setSuggestions(new ArrayList<Suggestion>());
        for (CityInfo cityInfo: relevantCities) {
            Suggestion currentSuggestion = new Suggestion();
            String fullCityName = new StringBuilder()
                    .append(cityInfo.getName())
                    .append(", ")
                    .append(cityInfo.getStateOrProvince())
                    .append(", ")
                    .append(cityInfo.getCountry())
                    .toString();
            currentSuggestion.setName(fullCityName);
            currentSuggestion.setScore(BigDecimal.valueOf(0.5d));
            currentSuggestion.setLongitude(String.valueOf(cityInfo.getLongitude()));
            currentSuggestion.setLatitude(String.valueOf(cityInfo.getLatitude()));
            suggestionResponse.addSuggestionsItem(currentSuggestion);
        }
        return suggestionResponse;
    }

    @Override
    public void clearCache() {
        cityService.clearCache();
    }
}

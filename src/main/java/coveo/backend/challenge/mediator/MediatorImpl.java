package coveo.backend.challenge.mediator;

import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.Suggestion;
import coveo.backend.challenge.model.SuggestionResponse;
import coveo.backend.challenge.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        SuggestionResponse suggestionResponse = new SuggestionResponse();
        CityInfo montreal = allCitiesMap.get(6077243L);
        Suggestion montrealSuggestion = new Suggestion();
        StringBuilder cityNameStringBuilder = new StringBuilder();
        cityNameStringBuilder.append(montreal.getName());
        cityNameStringBuilder.append(", ");
        cityNameStringBuilder.append(montreal.getStateOrProvince());
        cityNameStringBuilder.append(", ");
        cityNameStringBuilder.append(montreal.getCountry());
        montrealSuggestion.setName(cityNameStringBuilder.toString());
        montrealSuggestion.setScore(BigDecimal.valueOf(0.5d));
        montrealSuggestion.setLongitude(String.valueOf(montreal.getLongitude()));
        montrealSuggestion.setLatitude(String.valueOf(montreal.getLatitude()));
        suggestionResponse.addSuggestionsItem(montrealSuggestion);
        return suggestionResponse;
    }
}

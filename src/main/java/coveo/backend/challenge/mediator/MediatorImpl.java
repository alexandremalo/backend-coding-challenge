package coveo.backend.challenge.mediator;

import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.CoveoRESTException;
import coveo.backend.challenge.model.SuggestionResponse;
import coveo.backend.challenge.service.CityScoreService;
import coveo.backend.challenge.service.CityService;
import coveo.backend.challenge.util.CityFinder;
import coveo.backend.challenge.util.SuggestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MediatorImpl implements Mediator {

    private CityService cityService;
    private CityScoreService cityScoreService;

    @Autowired
    public MediatorImpl(CityService cityService, CityScoreService cityScoreService) {
        this.cityService = cityService;
        this.cityScoreService = cityScoreService;
    }

    @Override
    public SuggestionResponse getSuggestions(String query, String longitude, String latitude) {
        Map<Long, CityInfo> allCitiesMap = cityService.getAllCities();
        CityFinder cityFinder = new CityFinder();
        List<CityInfo> relevantCities = cityFinder.getRelevantCity(query, allCitiesMap);
        if (!StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)) {
            Double longitudeDouble;
            Double latitudeDouble;
            try {
                longitudeDouble = Double.parseDouble(longitude);
                latitudeDouble = Double.parseDouble(latitude);
            } catch (Exception e) {
                throw new CoveoRESTException(400, "Invalid Location parameters", "ex: latitude=43.70011&longitude=-79.4163", e.getMessage());
            }
            cityScoreService.scoreAndSortCities(relevantCities, query, longitudeDouble, latitudeDouble);
        } else {
            cityScoreService.scoreAndSortCities(relevantCities, query);
        }
        SuggestionResponse suggestionResponse = new SuggestionResponse();
        suggestionResponse.setSuggestions(new ArrayList<>());
        for (CityInfo cityInfo : relevantCities) {
            suggestionResponse.addSuggestionsItem(SuggestionMapper.fromCityInfo(cityInfo));
        }
        return suggestionResponse;
    }

    @Override
    public void clearCache() {
        cityService.clearCache();
    }
}

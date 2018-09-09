package coveo.backend.challenge.mediator;

import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.SuggestionResponse;
import coveo.backend.challenge.service.CityScoreService;
import coveo.backend.challenge.service.CityService;
import coveo.backend.challenge.util.CityFinder;
import coveo.backend.challenge.exception.CoveoExceptionHelper;
import coveo.backend.challenge.util.SuggestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.Semaphore;

@Component
public class MediatorImpl implements Mediator {

    private CityService cityService;
    private CityScoreService cityScoreService;
    private Semaphore allCitiesSemaphore;

    @Autowired
    public MediatorImpl(CityService cityService, CityScoreService cityScoreService) {
        this.cityService = cityService;
        this.cityScoreService = cityScoreService;
        allCitiesSemaphore = new Semaphore(1);
    }

    @Override
    public SuggestionResponse getSuggestions(String query, String longitude, String latitude) {
        //Ensuring that allCitiesMap object is not currently being modify
        try{
            allCitiesSemaphore.acquire();
        } catch (InterruptedException ex){
            CoveoExceptionHelper.throwSemaphoreAcquireErrorGet(ex);
        }

        //Getting list of cities to score
        List<CityInfo> relevantCities = CityFinder.getRelevantCity(query, cityService.getAllCities());
        allCitiesSemaphore.release();

        //Evaluating if which scoring method to call (with or without distance score)
        if (!StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)) {
            Double longitudeDouble = 0d;
            Double latitudeDouble = 0d;
            try {
                longitudeDouble = Double.parseDouble(longitude);
                latitudeDouble = Double.parseDouble(latitude);
            } catch (Exception ex) {
                CoveoExceptionHelper.throwLocationParamError(ex);
            }
            //Won't reach this statement if the Double parsing failed (so do not worry about long=0d, lat=0d)
            cityScoreService.scoreAndSortCities(relevantCities, query, longitudeDouble, latitudeDouble);
        } else {
            cityScoreService.scoreAndSortCities(relevantCities, query);
        }

        //Converting Back-end Model to Response Model
        return SuggestionMapper.suggestionResponseFromCityInfoList(relevantCities);
    }

    @Override
    public void clearCache() {
        cityService.clearCache(allCitiesSemaphore);
    }
}

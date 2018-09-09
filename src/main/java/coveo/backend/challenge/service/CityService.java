package coveo.backend.challenge.service;

import coveo.backend.challenge.model.CityInfo;

import java.util.Map;
import java.util.concurrent.Semaphore;

public interface CityService {
    Map<Long, CityInfo> getAllCities();
    void clearCache(Semaphore allCitiesSemaphore);
}

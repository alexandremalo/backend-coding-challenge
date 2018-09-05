package coveo.backend.challenge.service;

import coveo.backend.challenge.model.CityInfo;

import java.util.Map;

public interface CityService {
    Map<Long, CityInfo> getAllCities();
    void clearCache();
}

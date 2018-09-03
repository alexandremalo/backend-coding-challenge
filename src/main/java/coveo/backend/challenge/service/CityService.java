package coveo.backend.challenge.service;

import coveo.backend.challenge.model.CityInfo;

import java.util.Map;

public interface CityService {
    public Map<Long, CityInfo> getAllCities();
}

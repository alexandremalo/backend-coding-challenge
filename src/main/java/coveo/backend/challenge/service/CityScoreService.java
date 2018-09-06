package coveo.backend.challenge.service;

import coveo.backend.challenge.model.CityInfo;

import java.util.List;

public interface CityScoreService {
    void scoreAndSortCities(List<CityInfo> cityInfoList, String query, double longitude, double latitude);
    void scoreAndSortCities(List<CityInfo> cityInfoList, String query);
}

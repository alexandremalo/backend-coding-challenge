package coveo.backend.challenge.service;

import coveo.backend.challenge.model.CityInfo;

import java.util.List;

public interface CityScoreService {
    List<CityInfo> scoreAndSortCities(List<CityInfo> cityInfoList, double longitude, double latitude);
    List<CityInfo> scoreAndSortCities(List<CityInfo> cityInfoList);
}

package coveo.backend.challenge.service;

import coveo.backend.challenge.config.WeightingConfig;
import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.ScoreMetadata;
import coveo.backend.challenge.util.CoordinatesDistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityScoreServiceImpl implements CityScoreService {

    private WeightingConfig weightingConfig;

    @Autowired
    public CityScoreServiceImpl(WeightingConfig weightingConfig) {
        this.weightingConfig = weightingConfig;
    }

    @Override
    public List<CityInfo> scoreAndSortCities(List<CityInfo> cityInfoList, double longitude, double latitude) {
        List<CityInfo> scoredAndSortedCities = new ArrayList<CityInfo>();
        if (!cityInfoList.isEmpty()) {

            ScoreMetadata scoreMetadata = feedMinMax(cityInfoList, longitude, latitude);


            System.out.println("stats");

            System.out.println(scoreMetadata.getMaxDistance());
            System.out.println(scoreMetadata.getMinDistance());
            System.out.println(scoreMetadata.getMaxPopulation());
            System.out.println(scoreMetadata.getMinPopulation());
        }
        return scoredAndSortedCities;
    }

    @Override
    public List<CityInfo> scoreAndSortCities(List<CityInfo> cityInfoList) {
        return null;
    }

    private ScoreMetadata feedMinMax(List<CityInfo> cityInfoList, double longitude, double latitude) {
        CityInfo initCity = cityInfoList.get(0);
        double minDistance = CoordinatesDistanceUtil.getDistanceBetweenTwoCoordinates(longitude, latitude, initCity.getLongitude(), initCity.getLatitude());
        ScoreMetadata scoreMetadata = new ScoreMetadata(cityInfoList.get(0).getPopulation(), 0, minDistance, 0);
        for (CityInfo cityInfo: cityInfoList) {
            if (cityInfo.getPopulation() > scoreMetadata.getMaxPopulation())
                scoreMetadata.setMaxPopulation(cityInfo.getPopulation());
            else if (cityInfo.getPopulation() < scoreMetadata.getMinPopulation())
                scoreMetadata.setMinPopulation(cityInfo.getPopulation());

            double tempDistance = CoordinatesDistanceUtil.getDistanceBetweenTwoCoordinates(longitude, latitude, cityInfo.getLongitude(), cityInfo.getLatitude());
            if (tempDistance > scoreMetadata.getMaxDistance())
                scoreMetadata.setMaxDistance(tempDistance);
            else if (tempDistance < scoreMetadata.getMinDistance())
                scoreMetadata.setMinDistance(tempDistance);
        }
        return scoreMetadata;
    }

    private double getDistanceScore() {
        return 0d;
    }


}

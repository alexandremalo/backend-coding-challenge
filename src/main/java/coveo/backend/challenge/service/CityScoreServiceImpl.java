package coveo.backend.challenge.service;

import coveo.backend.challenge.config.WeightingConfig;
import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.ScoreMetadata;
import coveo.backend.challenge.util.CoordinatesDistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Component
public class CityScoreServiceImpl implements CityScoreService {

    private WeightingConfig weightingConfig;

    @Autowired
    public CityScoreServiceImpl(WeightingConfig weightingConfig) {
        this.weightingConfig = weightingConfig;
    }

    @Override
    public void scoreAndSortCities(List<CityInfo> cityInfoList, String query, double longitude, double latitude) {
        if (!cityInfoList.isEmpty()) {
            ScoreMetadata scoreMetadata = feedMinMax(cityInfoList, longitude, latitude, true);
            for (CityInfo cityInfo : cityInfoList) {
                double distanceScore = 1 - getMetricScore(getDistance(cityInfo, longitude, latitude), scoreMetadata.getMinDistance(), scoreMetadata.getMaxDistance());
                double populationScore = getMetricScore(cityInfo.getPopulation(), scoreMetadata.getMinPopulation(), scoreMetadata.getMaxPopulation());
                double nameScore = getNameScore(cityInfo, query);
                applyFinalScore(cityInfo, nameScore, populationScore, distanceScore);
            }
        }
        Collections.sort(cityInfoList, (o1, o2) -> o1.compareTo(o2));
    }

    @Override
    public void scoreAndSortCities(List<CityInfo> cityInfoList, String query) {
        if (!cityInfoList.isEmpty()) {
            ScoreMetadata scoreMetadata = feedMinMax(cityInfoList, 0, 0, false);
            for (CityInfo cityInfo : cityInfoList) {
                double populationScore = getMetricScore(cityInfo.getPopulation(), scoreMetadata.getMinPopulation(), scoreMetadata.getMaxPopulation());
                double nameScore = getNameScore(cityInfo, query);
                applyFinalScore(cityInfo, nameScore, populationScore);
            }
        }
        Collections.sort(cityInfoList, (o1, o2) -> o1.compareTo(o2));
    }

    private ScoreMetadata feedMinMax(List<CityInfo> cityInfoList, double longitude, double latitude, boolean calculateDistance) {
        CityInfo initCity = cityInfoList.get(0);
        double minDistance = CoordinatesDistanceUtil.getDistanceBetweenTwoCoordinates(longitude, latitude, initCity.getLongitude(), initCity.getLatitude());
        ScoreMetadata scoreMetadata = new ScoreMetadata(cityInfoList.get(0).getPopulation(), 0, minDistance, 0);
        for (CityInfo cityInfo : cityInfoList) {
            if (cityInfo.getPopulation() > scoreMetadata.getMaxPopulation())
                scoreMetadata.setMaxPopulation(cityInfo.getPopulation());
            else if (cityInfo.getPopulation() < scoreMetadata.getMinPopulation())
                scoreMetadata.setMinPopulation(cityInfo.getPopulation());
            if(calculateDistance){
                int tempDistance = (int) Math.round(CoordinatesDistanceUtil.getDistanceBetweenTwoCoordinates(longitude, latitude, cityInfo.getLongitude(), cityInfo.getLatitude()));
                if (tempDistance > scoreMetadata.getMaxDistance())
                    scoreMetadata.setMaxDistance(tempDistance);
                else if (tempDistance < scoreMetadata.getMinDistance())
                    scoreMetadata.setMinDistance(tempDistance);
            }
        }
        return scoreMetadata;
    }

    private int getDistance(CityInfo cityInfo, double longitude, double latitude) {
        return (int) Math.round(Math.round(CoordinatesDistanceUtil.getDistanceBetweenTwoCoordinates(longitude, latitude, cityInfo.getLongitude(), cityInfo.getLatitude())));
    }

    //Min-Max feature scaling
    //https://en.wikipedia.org/wiki/Feature_scaling
    private double getMetricScore(double value, double min, double max) {
        return ((value - min) / (max - min));
    }

    private double getNameScore(CityInfo cityInfo, String query) {
        double nameScore = 0d;
        if (cityInfo.getName().toLowerCase().equals(query.toLowerCase())){
            nameScore = 1d;
        } else if (cityInfo.getName().toLowerCase().startsWith(query.toLowerCase())) {
            nameScore = 0.7d;
        } else if (!StringUtils.isEmpty(cityInfo.getAltName())) {
            String[] altNames = cityInfo.getAltName().split(",");
            for (String name : altNames) {
                if (name.toLowerCase().equals(query.toLowerCase())) {
                    nameScore = 0.9d;
                    break;
                }
                if (name.toLowerCase().startsWith(query.toLowerCase())) {
                    nameScore = 0.6d;
                    break;
                }
            }
        }
        return nameScore;
    }

    private void applyFinalScore(CityInfo cityInfo, double nameScore, double populationScore, double distanceScore) {
        double maxScore = weightingConfig.getName() + weightingConfig.getPopulation() + weightingConfig.getDistance();
        double finalScore = ((nameScore * weightingConfig.getName()) +
                (populationScore * weightingConfig.getPopulation()) +
                (distanceScore * weightingConfig.getDistance()))
                / maxScore;
        cityInfo.setScore(formatDoubleValue(finalScore));
    }

    private void applyFinalScore(CityInfo cityInfo, double nameScore, double populationScore) {
        double maxScore = weightingConfig.getName() + weightingConfig.getPopulation();
        double finalScore = ((nameScore * weightingConfig.getName()) +
                (populationScore * weightingConfig.getPopulation()))
                / maxScore;
        cityInfo.setScore(formatDoubleValue(finalScore));
    }

    //https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
    private double formatDoubleValue(double preciseDouble) {
        return (double) Math.round(preciseDouble * 100000d) / 100000d;
    }


}

package coveo.backend.challenge.util;

import coveo.backend.challenge.model.CityInfo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityFinder {
    public static List<CityInfo> getRelevantCity(String query, Map<Long, CityInfo> allCitiesMap) {
        List<CityInfo> relevantCities = new ArrayList<CityInfo>();
        //Return empty list if query is null or too short (must be >= 3)
        if (!StringUtils.isEmpty(query) && query.length() >= 3){
            for (Map.Entry<Long, CityInfo> entry : allCitiesMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().getName() != null){
                    if(entry.getValue().getName().toLowerCase().contains(query.toLowerCase()) ||
                            entry.getValue().getAltName().toLowerCase().contains(query.toLowerCase())){
                        relevantCities.add(entry.getValue());
                    }
                }
            }
        }
        return relevantCities;
    }
}

package coveo.backend.challenge.util;

import coveo.backend.challenge.config.AdminCodeMapping;
import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.Suggestion;

import java.math.BigDecimal;
import java.util.Map;

public class SuggestionMapper {

    public static Suggestion fromCityInfo(CityInfo cityInfo) {
        Suggestion suggestion = new Suggestion();
        String fullCityName = new StringBuilder()
                .append(cityInfo.getName())
                .append(", ")
                .append(getMappedStateOrProvince(cityInfo.getStateOrProvince(), cityInfo.getCountry()))
                .append(", ")
                .append(getMappedCountry(cityInfo.getCountry()))
                .toString();
        suggestion.setName(fullCityName);
        suggestion.setScore(BigDecimal.valueOf(cityInfo.getScore()));
        suggestion.setLongitude(String.valueOf(cityInfo.getLongitude()));
        suggestion.setLatitude(String.valueOf(cityInfo.getLatitude()));
        return suggestion;
    }

    private static String getMappedStateOrProvince(String rawStateOrProvince, String rawCountry) {
        String stateOrProvince = rawStateOrProvince;
        Map<String, String> tempProvinceOrStateMap = AdminCodeMapping.ADMIN_2_CODE_MAPPING.get(rawCountry);
        if (tempProvinceOrStateMap != null) {
            String mappedStateOrProvince = tempProvinceOrStateMap.get(rawStateOrProvince);
            if(mappedStateOrProvince != null){
                stateOrProvince = mappedStateOrProvince;
            }
        }
        return stateOrProvince;
    }

    private static String getMappedCountry(String rawCountry) {
        String countryName = rawCountry;
        String mappedCountryName = AdminCodeMapping.COUNTRY_MAPPING.get(rawCountry);
        if(mappedCountryName != null){
            countryName = mappedCountryName;
        }
        return countryName;
    }
}

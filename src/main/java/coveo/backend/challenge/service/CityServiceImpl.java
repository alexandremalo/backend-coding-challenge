package coveo.backend.challenge.service;

import coveo.backend.challenge.config.CitiesDataSource;
import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.CoveoRESTException;
import coveo.backend.challenge.util.CityInfoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class CityServiceImpl implements CityService {

    private CitiesDataSource citiesDataSource;

    private final int ID_COLUMN = 0;
    private final int NAME_COLUMN = 1;
    private final int LATITUDE_COLUMN = 4;
    private final int LONGITUDE_COLUMN = 5;
    private final int POPULATION_COLUMN = 14;
    private final int COUNTRY_COLUMN = 8;
    private final int PROVINCE_OR_STATE_COLUMN = 10;

    private final String TAB_SPLITTER = "\t";

    @Autowired
    public CityServiceImpl(CitiesDataSource citiesDataSource){
        this.citiesDataSource = citiesDataSource;
    }

    @Override
    public Map<Long, CityInfo> getAllCities() {
        Map<Long, CityInfo> allCitiesMap = new HashMap<Long, CityInfo>();
        URL citiesDataURL;
        try {
            String url = citiesDataSource.getCitiesFile();
            citiesDataURL = new URL(url);
            BufferedReader citiesBR = new BufferedReader(new InputStreamReader(citiesDataURL.openStream()));
            String line;
            citiesBR.readLine();
            line = citiesBR.readLine();
            while (line != null) {
                CityInfo cityInfo = cityInfoFromFileLine(line);
                if(cityInfo != null)
                    allCitiesMap.put(cityInfo.getId(), cityInfo);
                line = citiesBR.readLine();
            }
            citiesBR.close();

        } catch (CoveoRESTException e) {
            throw e;
        } catch (Exception e){
            throw new CoveoRESTException(500, "Unable to fetch data from URL", "Unable to load remote TSV file", e.getMessage());
        }
        return allCitiesMap;
    }

    private CityInfo cityInfoFromFileLine(String line){
        CityInfo cityInfo = null;
        try{
            String[] splittedCityLine = line.split(TAB_SPLITTER);
            CityInfoBuilder cityInfoBuilder = new CityInfoBuilder();
            cityInfo = cityInfoBuilder
                    .id(Long.valueOf(splittedCityLine[ID_COLUMN]))
                    .name(splittedCityLine[NAME_COLUMN])
                    .country(splittedCityLine[COUNTRY_COLUMN])
                    .stateOrProvince(splittedCityLine[PROVINCE_OR_STATE_COLUMN])
                    .longitude(Double.valueOf(splittedCityLine[LONGITUDE_COLUMN]))
                    .latitude(Double.valueOf(splittedCityLine[LATITUDE_COLUMN]))
                    .population(Long.valueOf(splittedCityLine[POPULATION_COLUMN]))
                    .createCityInfo();
        } catch (Exception e){
            throw new CoveoRESTException(500, "Was not able to parse TSV line", "Bad data in TSV", e.getMessage());
        }
        return cityInfo;
    }
}

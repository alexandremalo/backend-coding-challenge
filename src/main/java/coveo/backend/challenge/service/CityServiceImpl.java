package coveo.backend.challenge.service;

import coveo.backend.challenge.config.CitiesDataSource;
import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.CoveoException;
import coveo.backend.challenge.util.CityInfoBuilder;
import coveo.backend.challenge.util.CoveoExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

@Component
public class CityServiceImpl implements CityService {

    private CitiesDataSource citiesDataSource;

    private Map<Long, CityInfo> allCitiesMap;

    private final int ID_COLUMN = 0;
    private final int NAME_COLUMN = 1;
    private final int ALT_NAME_COLUMN = 3;
    private final int LATITUDE_COLUMN = 4;
    private final int LONGITUDE_COLUMN = 5;
    private final int POPULATION_COLUMN = 14;
    private final int COUNTRY_COLUMN = 8;
    private final int PROVINCE_OR_STATE_COLUMN = 10;

    private final String TAB_SPLITTER = "\t";

    @Autowired
    public CityServiceImpl(CitiesDataSource citiesDataSource) {
        this.citiesDataSource = citiesDataSource;
    }

    @Override
    public Map<Long, CityInfo> getAllCities() {
        if (allCitiesMap == null) {
            fetchAllCities(null);
        }
        return allCitiesMap;
    }

    private CityInfo cityInfoFromFileLine(String line) {
        CityInfo cityInfo = null;
        try {
            String[] splittedCityLine = line.split(TAB_SPLITTER);
            CityInfoBuilder cityInfoBuilder = new CityInfoBuilder();
            cityInfo = cityInfoBuilder
                    .id(Long.valueOf(splittedCityLine[ID_COLUMN]))
                    .name(splittedCityLine[NAME_COLUMN])
                    .altName(splittedCityLine[ALT_NAME_COLUMN])
                    .country(splittedCityLine[COUNTRY_COLUMN])
                    .stateOrProvince(splittedCityLine[PROVINCE_OR_STATE_COLUMN])
                    .longitude(Double.valueOf(splittedCityLine[LONGITUDE_COLUMN]))
                    .latitude(Double.valueOf(splittedCityLine[LATITUDE_COLUMN]))
                    .population(Integer.valueOf(splittedCityLine[POPULATION_COLUMN]))
                    .createCityInfo();
        } catch (Exception ex) {
            CoveoExceptionHelper.throwCityDataParseError(ex);
        }
        return cityInfo;
    }

    @Override
    public void clearCache(Semaphore allCitiesSemaphore) {
        fetchAllCities(allCitiesSemaphore);
    }

    private void fetchAllCities(Semaphore allCitiesSemaphore) {
        Map<Long, CityInfo> fetchedCitiesMap = new HashMap<Long, CityInfo>();
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
                if (cityInfo != null)
                    fetchedCitiesMap.put(cityInfo.getId(), cityInfo);
                line = citiesBR.readLine();
            }
            citiesBR.close();

        } catch (CoveoException e) {
            throw e;
        } catch (Exception ex) {
            CoveoExceptionHelper.throwCannotFetchCityData(ex);
        }
        affectNewCityMap(fetchedCitiesMap, allCitiesSemaphore);
    }

    private void affectNewCityMap(Map<Long, CityInfo> newAllCitiesMap, Semaphore allCitiesSemaphore) {
        //Ensure no other thread is currently using AllCitiesMap object while updating it
        if(allCitiesSemaphore != null){
            try{
                allCitiesSemaphore.acquire();
            } catch (InterruptedException ex){
                CoveoExceptionHelper.throwSemaphoreAcquireErrorUpdate(ex);
            }
        }
        if (allCitiesMap != null) {
            allCitiesMap.clear();
        } else {
            allCitiesMap = new HashMap<>();
        }
        allCitiesMap.putAll(newAllCitiesMap);
        if(allCitiesSemaphore != null)
            allCitiesSemaphore.release();
    }

}

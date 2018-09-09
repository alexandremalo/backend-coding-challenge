package coveo.backend.challenge.service;

import coveo.backend.challenge.config.CitiesDataSource;
import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.exception.CoveoException;
import coveo.backend.challenge.util.CityInfoBuilder;
import coveo.backend.challenge.exception.CoveoExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    private final String ID_COLUMN = "id";
    private final String NAME_COLUMN = "name";
    private final String ALT_NAME_COLUMN = "alt_name";
    private final String LATITUDE_COLUMN = "lat";
    private final String LONGITUDE_COLUMN = "long";
    private final String POPULATION_COLUMN = "population";
    private final String COUNTRY_COLUMN = "country";
    private final String PROVINCE_OR_STATE_COLUMN = "admin1";

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
            Map<String, Integer> columnNumberMap = getColumnNumbersFromHeaderLine(citiesBR.readLine());
            String line = citiesBR.readLine();
            while (line != null) {
                CityInfo cityInfo = cityInfoFromFileLine(line, columnNumberMap);
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
        if (allCitiesSemaphore != null) {
            try {
                allCitiesSemaphore.acquire();
            } catch (InterruptedException ex) {
                CoveoExceptionHelper.throwSemaphoreAcquireErrorUpdate(ex);
            }
        }
        if (allCitiesMap != null) {
            allCitiesMap.clear();
        } else {
            allCitiesMap = new HashMap<>();
        }
        allCitiesMap.putAll(newAllCitiesMap);
        if (allCitiesSemaphore != null)
            allCitiesSemaphore.release();
    }

    private Map<String, Integer> getColumnNumbersFromHeaderLine(String headerLine) {
        Map<String, Integer> columnNumberMap = new HashMap<>();
        if (!StringUtils.isEmpty(headerLine)) {
            String[] splittedHeaderLine = headerLine.split(TAB_SPLITTER);
            int colIndex = 0;
            for (String columnTitle : splittedHeaderLine) {
                if (ID_COLUMN.equals(columnTitle) ||
                        NAME_COLUMN.equals(columnTitle) ||
                        ALT_NAME_COLUMN.equals(columnTitle) ||
                        LATITUDE_COLUMN.equals(columnTitle) ||
                        LONGITUDE_COLUMN.equals(columnTitle) ||
                        POPULATION_COLUMN.equals(columnTitle) ||
                        COUNTRY_COLUMN.equals(columnTitle) ||
                        PROVINCE_OR_STATE_COLUMN.equals(columnTitle)) {
                    columnNumberMap.put(columnTitle, colIndex);
                }
                colIndex++;
            }
        }
        return columnNumberMap;
    }

    private CityInfo cityInfoFromFileLine(String line, Map<String, Integer> columnNumberMap) {
        CityInfo cityInfo = null;
        try {
            String[] splittedCityLine = line.split(TAB_SPLITTER);
            CityInfoBuilder cityInfoBuilder = new CityInfoBuilder();
            cityInfo = cityInfoBuilder
                    .id(Long.valueOf(splittedCityLine[columnNumberMap.get(ID_COLUMN)]))
                    .name(splittedCityLine[columnNumberMap.get(NAME_COLUMN)])
                    .altName(splittedCityLine[columnNumberMap.get(ALT_NAME_COLUMN)])
                    .country(splittedCityLine[columnNumberMap.get(COUNTRY_COLUMN)])
                    .stateOrProvince(splittedCityLine[columnNumberMap.get(PROVINCE_OR_STATE_COLUMN)])
                    .longitude(Double.valueOf(splittedCityLine[columnNumberMap.get(LONGITUDE_COLUMN)]))
                    .latitude(Double.valueOf(splittedCityLine[columnNumberMap.get(LATITUDE_COLUMN)]))
                    .population(Integer.valueOf(splittedCityLine[columnNumberMap.get(POPULATION_COLUMN)]))
                    .createCityInfo();
        } catch (Exception ex) {
            CoveoExceptionHelper.throwCityDataParseError(ex);
        }
        return cityInfo;
    }


}

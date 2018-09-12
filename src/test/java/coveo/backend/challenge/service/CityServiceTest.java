package coveo.backend.challenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import coveo.backend.challenge.config.CitiesDataSource;
import coveo.backend.challenge.model.CityInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static org.mockito.Mockito.*;

public class CityServiceTest {

    private CitiesDataSource citiesDataSource;
    private Map<Long, CityInfo> excpectedCityMap;
    private CityService cityService;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        citiesDataSource = mock(CitiesDataSource.class);
        cityService = new CityServiceImpl(citiesDataSource);
        excpectedCityMap = getValidCityMap();
        when(citiesDataSource.getCitiesFile()).thenReturn("src/test/resources/dataSourceFile_valid.txt");
    }

    @Test
    public void Should_Return_All_City_Map() throws Exception{
        Map<Long, CityInfo> actualCityMap = cityService.getAllCities();
        Assert.assertEquals(objectMapper.writeValueAsString(actualCityMap), objectMapper.writeValueAsString(excpectedCityMap));
        verify(citiesDataSource, times(1)).getCitiesFile();
    }

    @Test
    public void Should_Return_All_City_Map_From_Cache() throws Exception{
        cityService.getAllCities();
        Map<Long, CityInfo> actualCityMap = cityService.getAllCities();
        Assert.assertEquals(objectMapper.writeValueAsString(actualCityMap), objectMapper.writeValueAsString(excpectedCityMap));
        verify(citiesDataSource, times(1)).getCitiesFile();
    }

    @Test
    public void Should_Return_All_City_Map_From_Cleared_Cache() throws Exception{
        cityService.getAllCities();
        cityService.clearCache(new Semaphore(1));
        Map<Long, CityInfo> actualCityMap = cityService.getAllCities();
        Assert.assertEquals(objectMapper.writeValueAsString(actualCityMap), objectMapper.writeValueAsString(excpectedCityMap));
        verify(citiesDataSource, times(2)).getCitiesFile();
    }



    private Map<Long, CityInfo> getValidCityMap() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/cityMap_expected.json");
        return objectMapper.readValue(suggestionResponseValid, new TypeReference<Map<String,CityInfo>>() {});
    }
}

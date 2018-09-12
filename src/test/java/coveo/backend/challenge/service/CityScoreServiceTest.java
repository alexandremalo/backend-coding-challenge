package coveo.backend.challenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import coveo.backend.challenge.config.WeightingConfig;
import coveo.backend.challenge.model.CityInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CityScoreServiceTest {

    private WeightingConfig weightingConfig;
    private CityScoreService cityScoreService;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        weightingConfig = mock(WeightingConfig.class);
        cityScoreService = new CityScoreServiceImpl(weightingConfig);
        when(weightingConfig.getName()).thenReturn(3);
        when(weightingConfig.getDistance()).thenReturn(2);
        when(weightingConfig.getPopulation()).thenReturn(1);
    }

    @Test
    public void Should_Return_Scored_And_Sorted_Cities() throws Exception{
        List<CityInfo> actualCityList = cityScoreService.scoreAndSortCities(getRelevantCityList(), "Mon");
        Assert.assertEquals(objectMapper.writeValueAsString(actualCityList), objectMapper.writeValueAsString(getScoredAndSortedCityList()));
    }

    @Test
    public void Should_Return_Scored_And_Sorted_Cities_With_Location() throws Exception{
        List<CityInfo> actualCityList = cityScoreService.scoreAndSortCities(getRelevantCityList(), "Mon", -72.56582d, 45.65007d);
        Assert.assertEquals(objectMapper.writeValueAsString(actualCityList), objectMapper.writeValueAsString(getScoredAndSortedCityListWithLocation()));
    }

    private List<CityInfo> getRelevantCityList() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/relevantCityList_expected.json");
        return objectMapper.readValue(suggestionResponseValid, new TypeReference<List<CityInfo>>() {});
    }

    private List<CityInfo> getScoredAndSortedCityList() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/scoredAndSortedCityList_expected.json");
        return objectMapper.readValue(suggestionResponseValid, new TypeReference<List<CityInfo>>() {});
    }

    private List<CityInfo> getScoredAndSortedCityListWithLocation() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/scoredAndSortedCityListWithLocation_expected.json");
        return objectMapper.readValue(suggestionResponseValid, new TypeReference<List<CityInfo>>() {});
    }
}

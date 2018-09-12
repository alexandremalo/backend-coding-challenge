package coveo.backend.challenge.mediator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import coveo.backend.challenge.model.CityInfo;
import coveo.backend.challenge.model.SuggestionResponse;
import coveo.backend.challenge.service.CityScoreService;
import coveo.backend.challenge.service.CityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class MediatorTest {

    private CityService cityService;
    private CityScoreService cityScoreService;
    private Mediator mediator;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        cityService = mock(CityService.class);
        cityScoreService = mock(CityScoreService.class);
        mediator = new MediatorImpl(cityService, cityScoreService);
        when(cityService.getAllCities()).thenReturn(getValidCityMap());
        doNothing().when(cityService).clearCache(any());
        when(cityScoreService.scoreAndSortCities(any(), any())).thenReturn(getValidSortedCityList());
        when(cityScoreService.scoreAndSortCities(any(), any(), any(Double.class), any(Double.class))).thenReturn(getValidSortedCityListWithLocation());
    }

    @Test
    public void Should_Return_SuggestionResponse_Without_Location() throws Exception{
        SuggestionResponse actualSuggestionResponse = mediator.getSuggestions("Mon", "", "");
        Assert.assertEquals(objectMapper.writeValueAsString(actualSuggestionResponse), objectMapper.writeValueAsString(getValidSuggestionResponse()));
        verify(cityService, times(1)).getAllCities();
        verify(cityScoreService, times(1)).scoreAndSortCities(any(), any());
    }

    @Test
    public void Should_Return_SuggestionResponse_With_Location() throws Exception{
        SuggestionResponse actualSuggestionResponse = mediator.getSuggestions("Mon", "12.231", "32.213");
        Assert.assertEquals(objectMapper.writeValueAsString(actualSuggestionResponse), objectMapper.writeValueAsString(getValidSuggestionResponseWithLocation()));
        verify(cityService, times(1)).getAllCities();
        verify(cityScoreService, times(1)).scoreAndSortCities(any(), any(), anyDouble(), anyDouble());
    }

    @Test
    public void Should_Call_Clear_Cache() throws Exception{
        mediator.clearCache();
        verify(cityService, times(1)).clearCache(any());
    }

    private Map<Long, CityInfo> getValidCityMap() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/cityMap_expected.json");
        return objectMapper.readValue(suggestionResponseValid, new TypeReference<Map<String,CityInfo>>() {});
    }

    private List<CityInfo> getValidSortedCityList() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/scoredAndSortedCityList_expected.json");
        return objectMapper.readValue(suggestionResponseValid, new TypeReference<List<CityInfo>>() {});
    }

    private List<CityInfo> getValidSortedCityListWithLocation() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/scoredAndSortedCityListWithLocation_expected.json");
        return objectMapper.readValue(suggestionResponseValid, new TypeReference<List<CityInfo>>() {});
    }

    private SuggestionResponse getValidSuggestionResponse() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/suggestionResponse_valid.json");
        return objectMapper.readValue(suggestionResponseValid, SuggestionResponse.class);
    }

    private SuggestionResponse getValidSuggestionResponseWithLocation() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/suggestionResponseWithLocation_valid.json");
        return objectMapper.readValue(suggestionResponseValid, SuggestionResponse.class);
    }
}

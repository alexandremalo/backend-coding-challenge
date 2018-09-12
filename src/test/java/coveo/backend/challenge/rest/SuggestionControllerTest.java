package coveo.backend.challenge.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import coveo.backend.challenge.mediator.Mediator;
import coveo.backend.challenge.model.SuggestionResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.io.File;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SuggestionControllerTest {

    Mediator mediator;
    SuggestionResponse expectedSuggestionResponse;
    SuggestionController suggestionController;

    @Before
    public void setUp() throws Exception{
        mediator = mock(Mediator.class);
        suggestionController = new SuggestionController(mediator);
        expectedSuggestionResponse = getValidSuggestionResponse();
        doNothing().when(mediator).clearCache();
        when(mediator.getSuggestions(any(), any(), any())).thenReturn(expectedSuggestionResponse);
    }

    @Test
    public void Should_Call_Mediator_Get_Suggestion() {
        ResponseEntity<SuggestionResponse> actualSuggestionResponse = suggestionController.getSuggestions("test", "45.2313", "-123.1234");
        Assert.assertEquals(expectedSuggestionResponse, actualSuggestionResponse.getBody());
        verify(mediator, times(1)).getSuggestions(anyString(), anyString(), anyString());
    }

    @Test
    public void Should_Call_Mediator_Clear_Cache() {
        suggestionController.clearCache();
        verify(mediator, times(1)).clearCache();
    }

    private SuggestionResponse getValidSuggestionResponse() throws Exception{
        File suggestionResponseValid = new File("src/test/resources/suggestionResponse_valid.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(suggestionResponseValid, SuggestionResponse.class);
    }
}

package coveo.backend.challenge.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class TestRest {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity test(){
        return ResponseEntity.ok("ok, good!");
    }
}

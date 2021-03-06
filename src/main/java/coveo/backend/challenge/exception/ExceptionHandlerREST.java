package coveo.backend.challenge.exception;

import coveo.backend.challenge.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springfox.documentation.annotations.ApiIgnore;

@ControllerAdvice
@ApiIgnore
public class ExceptionHandlerREST extends ResponseEntityExceptionHandler {

        @ExceptionHandler(CoveoException.class)
        protected ResponseEntity<ErrorResponse> handleServiceException(CoveoException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode(ex.getErrorCode());
            errorResponse.setDevMessage(ex.getMessage());
            errorResponse.setMessage(ex.getCustomMessage());
            errorResponse.setError(ex.getErrorName());
            errorResponse.setDevMessage(ex.getDevMessage());
            return ResponseEntity.status(ex.getErrorCode()).body(errorResponse);
        }
}

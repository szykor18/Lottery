package pl.lotto.infrastructure.loginandregister.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lotto.domain.loginandregister.UserAlreadyExistsException;

@ControllerAdvice
@Log4j2
public class RegistrationErrorHandler {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUsernameAlreadyExists(UserAlreadyExistsException exception) {
        log.error(exception.getMessage());
        return new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
    }
}

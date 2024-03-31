package pl.lotto.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TokenControllerErrorHandler {

    private static final String BAD_CREDENTIALS_MESSAGE = "Bad Credentials";
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleExceptionUnauthorized() {
        return new ErrorResponse(BAD_CREDENTIALS_MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}

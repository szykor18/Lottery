package pl.lotto.infrastructure.resultannouncer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lotto.domain.resultchecker.PlayerNotFoundByHashException;

@ControllerAdvice
@Log4j2
public class ResultAnnouncerControllerErrorHandler {

    @ExceptionHandler(PlayerNotFoundByHashException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultAnnouncerErrorResponse handlePlayerNotFoundException(PlayerNotFoundByHashException playerNotFoundException) {
        String notFoundMessage = playerNotFoundException.getMessage();
        log.error(notFoundMessage);
        return new ResultAnnouncerErrorResponse(notFoundMessage, HttpStatus.NOT_FOUND);
    }
}

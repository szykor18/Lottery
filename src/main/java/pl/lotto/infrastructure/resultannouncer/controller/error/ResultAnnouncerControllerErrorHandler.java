package pl.lotto.infrastructure.resultannouncer.controller.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.lotto.domain.resultchecker.PlayerNotFoundByHashException;
import pl.lotto.domain.resultchecker.TicketFoundButDrawNotHappenedYet;

@RestControllerAdvice
@Log4j2
public class ResultAnnouncerControllerErrorHandler {

    @ExceptionHandler(PlayerNotFoundByHashException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultAnnouncerErrorResponse handlePlayerNotFoundException(PlayerNotFoundByHashException playerNotFoundException) {
        String notFoundMessage = playerNotFoundException.getMessage();
        log.error(notFoundMessage);
        return new ResultAnnouncerErrorResponse(notFoundMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketFoundButDrawNotHappenedYet.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultAnnouncerErrorResponse handlePlayerFoundBotDrawNotHappenedException(TicketFoundButDrawNotHappenedYet exception) {
        String message = exception.getMessage();
        log.error(message);
        return new ResultAnnouncerErrorResponse(message, HttpStatus.OK);
    }
}

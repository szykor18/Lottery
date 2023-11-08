package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

public class ResultAnnouncerConfiguration {
    ResultAnnouncerFacade createForTests(ResultCheckerFacade resultCheckerFacade, PlayerResponseRepository playerResponseRepository, Clock clock){
        return new ResultAnnouncerFacade(resultCheckerFacade, playerResponseRepository, clock);
    }
}

package pl.lotto.domain.drawdategenerator;
import java.time.*;

public class DrawDateConfiguration {

    DrawDateFacade drawDateFacade() {
        return new DrawDateFacade(drawDateGenerator());
    }

    DrawDateGenerator drawDateGenerator() {
        return new DrawDateGenerator(clock());
    }
    Clock clock() {
        return Clock.systemUTC();
    }
}

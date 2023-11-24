package pl.lotto.domain.drawdategenerator;

import lombok.AllArgsConstructor;
import java.time.Clock;
import java.time.LocalDateTime;

@AllArgsConstructor
public class DrawDateGeneratorTestImpl implements DrawDateGenerable{
    private final Clock clock;

    @Override
    public LocalDateTime getNextDrawDate() {
        return new DrawDateGenerator(clock).getNextDrawDate();
    }
}

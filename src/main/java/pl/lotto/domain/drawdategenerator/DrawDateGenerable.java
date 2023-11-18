package pl.lotto.domain.drawdategenerator;

import java.time.LocalDateTime;

public interface DrawDateGenerable {
    LocalDateTime getNextDrawDate();
}

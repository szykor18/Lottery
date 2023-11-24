package pl.lotto.domain.drawdategenerator;

import lombok.AllArgsConstructor;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

@AllArgsConstructor
class DrawDateGenerator implements DrawDateGenerable{
    private final Clock clock;
    private static final LocalTime DRAW_TIME = LocalTime.of(12,0,0);
    private static final TemporalAdjuster NEXT_DRAW_DAY = TemporalAdjusters.next(DayOfWeek.SATURDAY);
    @Override
    public LocalDateTime getNextDrawDate() {
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        if (isSaturdayAndBeforeNoon(currentDateTime)) {
            return LocalDateTime.of(currentDateTime.toLocalDate(), DRAW_TIME);
        }
        LocalDate drawDate = currentDateTime.toLocalDate().with(NEXT_DRAW_DAY);
        return LocalDateTime.of(drawDate, DRAW_TIME);
    }

    private boolean isSaturdayAndBeforeNoon(LocalDateTime currentDateTime) {
        return currentDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) &&
                currentDateTime.toLocalTime().isBefore(DRAW_TIME);
    }
}

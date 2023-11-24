package pl.lotto.domain.drawdategenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DrawDateFacadeTest {

    ZoneId warsawZone = ZoneId.of("Europe/Warsaw");
    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023,11,4,14,0,0)
            .atZone(warsawZone).toInstant(), warsawZone);
    DrawDateFacade drawDateFacade = new DrawDateFacade(new DrawDateGeneratorTestImpl(clock));

    @Test
    public void should_return_correct_draw_date() {
        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        //when
        LocalDateTime localDateTime = drawDateFacade.getNextDrawDate();
        //then
        LocalDateTime expectedDate = LocalDateTime.of(2023, 11, 11, 12, 0 ,0);
        assertThat(localDateTime).isEqualTo(expectedDate);
    }
    @Test
    public void should_return_next_Saturday_when_date_is_Saturday_noon() {
        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        clock.setClockToLocalDateTime(LocalDateTime.of(2023,11,11,12,00));
        //when
        LocalDateTime localDateTime = drawDateFacade.getNextDrawDate();
        //then
        LocalDateTime expectedDate = LocalDateTime.of(2023,11,18,12,0,0);
        assertThat(localDateTime).isEqualTo(expectedDate);
    }
    @Test
    public void should_return_next_Saturday_when_date_is_Saturday_afternoon() {
        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        clock.setClockToLocalDateTime(LocalDateTime.of(2023,11,11,16,00));
        //when
        LocalDateTime localDateTime = drawDateFacade.getNextDrawDate();
        //then
        LocalDateTime expectedDate = LocalDateTime.of(2023,11,18,12,0,0);
        assertThat(localDateTime).isEqualTo(expectedDate);
    }

}

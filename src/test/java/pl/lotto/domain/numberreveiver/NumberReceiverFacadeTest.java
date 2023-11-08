package pl.lotto.domain.numberreveiver;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;
import pl.lotto.domain.numberreveiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numberreveiver.dto.TicketDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    ZoneId warsawZone = ZoneId.of("Europe/Warsaw");
    AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023,11,4,14,0,0)
            .atZone(warsawZone).toInstant(), warsawZone);
    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryTestImpl(),
            new HashGenerator(),
            new DrawDateGenerator(clock)
    );

    @Test
    public void should_return_success_when_user_gave_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("SUCCESS");
    }

    @Test
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("YOU SHOULD GIVE 6 NUMBERS");
    }

    @Test
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("YOU SHOULD GIVE 6 NUMBERS");
    }

    @Test
    public void should_return_failed_when_user_gave_at_least_one_number_out_of_range_of_1_to_99() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2000, 3, 4, 5, 6);
        //when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then
        assertThat(result.message()).isEqualTo("YOU SHOULD GIVE NUMBERS FROM 1 TO 99");
    }
    @Test
    public void should_return_save_to_database_when_user_gave_six_numbers() {
        //given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        LocalDateTime drawDate = LocalDateTime.of(2023,11,11,12,0,0);
        //when
        List<TicketDto> ticketDtos = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate);
        //then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .hash(result.ticketDto().hash())
                        .drawDate(drawDate)
                        .numbersFromUser(result.ticketDto().numbersFromUser())
                        .build()
        );
    }
    @Test
    public void should_return_correct_hash() {
        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        //when
        String response = numberReceiverFacade.inputNumbers(numbersFromUser).ticketDto().hash();
        //then
        assertThat(response).hasSize(36);
        assertThat(response).isNotNull();
    }
    @Test
    public void should_return_correct_draw_date() {
        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        //when
        LocalDateTime localDateTime = numberReceiverFacade.inputNumbers(numbersFromUser).ticketDto().drawDate();
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
        LocalDateTime localDateTime = numberReceiverFacade.inputNumbers(numbersFromUser).ticketDto().drawDate();
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
        LocalDateTime localDateTime = numberReceiverFacade.inputNumbers(numbersFromUser).ticketDto().drawDate();
        //then
        LocalDateTime expectedDate = LocalDateTime.of(2023,11,18,12,0,0);
        assertThat(localDateTime).isEqualTo(expectedDate);
    }
    @Test
    public void should_return_empty_collections_if_there_are_no_tickets() {
        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        clock.setClockToLocalDateTime(LocalDateTime.of(2023,11,05,15,00));
        LocalDateTime drawDate = LocalDateTime.now(clock);
        //when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate);
        //then
        assertThat(allTicketsByDate).isEmpty();
    }
    @Test
    public void should_return_empty_collections_if_given_date_is_after_next_drawDate() {
        //given
        Set<Integer> numbersFromUser = Set.of(1,2,3,4,5,6);
        clock.setClockToLocalDateTime(LocalDateTime.of(2023,11,05,15,00));
        InputNumbersResultDto numbersResultDto = numberReceiverFacade.inputNumbers(numbersFromUser);
        LocalDateTime drawDate = numbersResultDto.ticketDto().drawDate();
        //when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate.plusWeeks(1));
        //then
        assertThat(allTicketsByDate).isEmpty();
    }


}
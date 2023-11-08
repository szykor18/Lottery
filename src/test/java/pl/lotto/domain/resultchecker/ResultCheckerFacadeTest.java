package pl.lotto.domain.resultchecker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;
import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.PlayerDto;
import pl.lotto.domain.resultchecker.dto.PlayersResultsDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultCheckerFacadeTest {

    private final PlayerRepository playerRepository = new PlayerRepositoryTestImpl();
    private final NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = mock(WinningNumbersGeneratorFacade.class);
    ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberReceiverFacade, winningNumbersGeneratorFacade, new PlayerRepositoryTestImpl(),
            new WinnersRetriever());
    @Test
    public void should_generate_all_players_with_correct_message() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,11,12,0,0);
        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(
                WinningNumbersDto.builder()
                        .winningNumbers(Set.of(1,2,3,4,5,6))
                        .build()
        );
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(
                List.of(
                        TicketDto.builder()
                                .hash("001")
                                .numbersFromUser(Set.of(1,2,3,4,5,6))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("002")
                                .numbersFromUser(Set.of(1,2,7,8,9,10))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("003")
                                .numbersFromUser(Set.of(7,8,9,10,11,12))
                                .drawDate(drawDate)
                                .build()
                )
        );
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateResults();
        //when
        List<PlayerDto> results = playersResultsDto.results();
        //then
        PlayerDto playerDto = PlayerDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        PlayerDto playerDto2 = PlayerDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        PlayerDto playerDto3 = PlayerDto.builder()
                .hash("001")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        assertThat(results).contains(playerDto, playerDto2, playerDto3);
        String message = playersResultsDto.message();
        assertThat(message).isEqualTo("succeed to retrieve winners");
    }
    @Test
    public void should_generate_fail_message_when_winningNumbers_equal_null() {
        //given
        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(null)
                .build());
        //when
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateResults();
        //then
        String message = playersResultsDto.message();
        assertThat(message).isEqualTo("failed to retrieve winners");
    }
    @Test
    public void should_generate_fail_message_when_winningNumbers_is_empty() {
        //given
        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of())
                .build());
        //when
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateResults();
        //then
        String message = playersResultsDto.message();
        assertThat(message).isEqualTo("failed to retrieve winners");
    }
    @Test
    public void it_should_generate_result_with_correct_credentials() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023, 11, 11, 12, 0, 0);
        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());
        String hash = "001";
        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(
                List.of(TicketDto.builder()
                                .hash(hash)
                                .numbersFromUser(Set.of(7, 8, 9, 10, 11, 12))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("002")
                                .numbersFromUser(Set.of(7, 8, 9, 10, 11, 13))
                                .drawDate(drawDate)
                                .build(),
                        TicketDto.builder()
                                .hash("003")
                                .numbersFromUser(Set.of(7, 8, 9, 10, 11, 14))
                                .drawDate(drawDate)
                                .build())
        );
        resultCheckerFacade.generateResults();
        //when
        PlayerDto playerDto = resultCheckerFacade.findPlayerByHash(hash);
        //then
        PlayerDto expectedResult = PlayerDto.builder()
                .hash(hash)
                .numbers(Set.of(7, 8, 9, 10, 11, 12))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        assertThat(playerDto).isEqualTo(expectedResult);
    }
}
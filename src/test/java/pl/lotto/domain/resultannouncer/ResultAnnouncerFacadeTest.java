package pl.lotto.domain.resultannouncer;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.resultannouncer.dto.PlayerResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.lotto.domain.resultannouncer.MessageResponse.*;

class ResultAnnouncerFacadeTest {
    PlayerResponseRepository playerResponseRepository = new PlayerResponseRepositoryTestImpl();
    ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);
    @Test
    public void should_return_response_with_lose_message_if_ticket_not_winning() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,4,12,0,0);
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, playerResponseRepository, Clock.systemUTC());
        String hash = "100";
        PlayerDto playerDto = PlayerDto.builder()
                .hash("100")
                .numbers(Set.of(1,2,3,4,5,6))
                .hitNumbers(Set.of(1))
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(playerDto);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        PlayerResponseDto playerResponseDto = PlayerResponseDto.builder()
                .hash("100")
                .numbers(Set.of(1,2,3,4,5,6))
                .hitNumbers(Set.of(1))
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(playerResponseDto, LOSE_MESSAGE.info);
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }
    @Test
    public void should_return_response_with_win_message_if_ticket_is_winning_ticket() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,4,12,0,0);
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, playerResponseRepository, Clock.systemUTC());
        String hash = "100";
        PlayerDto resultDto = PlayerDto.builder()
                .hash("100")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        PlayerResponseDto playerResponseDto = PlayerResponseDto.builder()
                .hash("100")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(playerResponseDto, WIN_MESSAGE.info);
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void should_return_response_with_wait_message_if_date_is_before_announcement_time() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,11,12,0,0);
        String hash = "100";
        Clock clock = Clock.fixed(LocalDateTime.of(2023, 11, 8, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, playerResponseRepository, clock);
        PlayerDto playerDto = PlayerDto.builder()
                .hash("100")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(playerDto);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        PlayerResponseDto playerResponseDto = PlayerResponseDto.builder()
                .hash("100")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(playerResponseDto, WAIT_MESSAGE.info);
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void should_return_response_with_hash_does_not_exist_message_if_hash_does_not_exist() {
        //given
        String hash = "100";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, playerResponseRepository, Clock.systemUTC());

        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(null);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(null, HASH_DOES_NOT_EXIST_MESSAGE.info);
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void should_return_response_with_hash_does_not_exist_message_if_response_is_not_saved_to_db_yet() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,4,12,0,0);
        String hash = "100";
        PlayerDto resultDto = PlayerDto.builder()
                .hash("100")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(resultDto);

        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, playerResponseRepository, Clock.systemUTC());
        ResultAnnouncerResponseDto resultAnnouncerResponseDto1 = resultAnnouncerFacade.checkResult(hash);
        String underTest = resultAnnouncerResponseDto1.playerResponseDto().hash();
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(underTest);
        //then
        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(
                resultAnnouncerResponseDto.playerResponseDto()
                , ALREADY_CHECKED.info);
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }
}
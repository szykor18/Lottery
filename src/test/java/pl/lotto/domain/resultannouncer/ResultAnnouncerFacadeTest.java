package pl.lotto.domain.resultannouncer;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.lotto.domain.resultannouncer.ResultMessage.*;

class ResultAnnouncerFacadeTest {
    ResponseRepository responseRepository = new ResponseRepositoryTestImpl();
    ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);

    @Test
    public void should_return_response_with_lose_message_if_ticket_is_not_win() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,4,12,0,0);
        String hash = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, responseRepository, Clock.systemUTC());
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(
                PlayerDto.builder()
                        .hash(hash)
                        .numbers(Set.of(1,2,3,4,5,6))
                        .hitNumbers(Set.of(6))
                        .drawDate(drawDate)
                        .isWinner(false)
                        .build()
        );
        //when
        ResultAnnouncerDto resultAnnouncerDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash(hash)
                .numbers(Set.of(1,2,3,4,5,6))
                .wonNumbers(Set.of(6))
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        ResultAnnouncerDto expected = ResultAnnouncerDto.builder()
                .responseDto(responseDto)
                .message(LOSE_MESSAGE.info)
                .build();
    }
    @Test
    public void should_return_response_with_win_message_if_ticket_is_winning() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,4,12,0,0);
        String hash = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, responseRepository, Clock.systemUTC());
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(
                PlayerDto.builder()
                        .hash(hash)
                        .numbers(Set.of(1,2,3,4,5,6))
                        .hitNumbers(Set.of(1,2,3,4))
                        .drawDate(drawDate)
                        .isWinner(true)
                        .build()
        );
        //when
        ResultAnnouncerDto resultAnnouncerDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash(hash)
                .numbers(Set.of(1,2,3,4,5,6))
                .wonNumbers(Set.of(1,2,3,4))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultAnnouncerDto expected = ResultAnnouncerDto.builder()
                .responseDto(responseDto)
                .message(WIN_MESSAGE.info)
                .build();
    }
    @Test
    public void should_return_response_with_wait_message_if_date_is_before_announcement_time(){
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,11,12,0,0);
        String hash = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, responseRepository, Clock.systemUTC());
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(
                PlayerDto.builder()
                        .hash(hash)
                        .numbers(Set.of(1,2,3,4,5,6))
                        .hitNumbers(Set.of(1,2,3,4))
                        .drawDate(drawDate)
                        .isWinner(true)
                        .build()
        );
        //when
        ResultAnnouncerDto resultAnnouncerDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash(hash)
                .numbers(Set.of(1,2,3,4,5,6))
                .wonNumbers(Set.of(1,2,3,4))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultAnnouncerDto expected = ResultAnnouncerDto.builder()
                .responseDto(responseDto)
                .message(WAIT_MESSAGE.info)
                .build();
    }
    @Test
    public void should_return_response_with_hash_does_not_exist_message_if_hash_does_not_exist(){
        //given
        String hash = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, responseRepository, Clock.systemUTC());
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(null);
        //when
        ResultAnnouncerDto resultAnnouncerDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultAnnouncerDto expected = ResultAnnouncerDto.builder()
                .responseDto(null)
                .message(HASH_NOT_EXISTS_MESSAGE.info)
                .build();
    }
    @Test
    public void should_return_response_with_already_checked_message_user_already_got_the_response() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2023,11,4,12,0,0);
        String hash = "111";
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTests(resultCheckerFacade, responseRepository, Clock.systemUTC());
        when(resultCheckerFacade.findPlayerByHash(hash)).thenReturn(
                PlayerDto.builder()
                        .hash(hash)
                        .numbers(Set.of(1,2,3,4,5,6))
                        .hitNumbers(Set.of(1,2,3,4))
                        .drawDate(drawDate)
                        .isWinner(true)
                        .build()
        );
        ResultAnnouncerDto resultAnnouncerDto = resultAnnouncerFacade.checkResult(hash);
        //when
        ResultAnnouncerDto resultAnnouncerDto2 = resultAnnouncerFacade.checkResult(resultAnnouncerDto
                .responseDto().hash());
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash(hash)
                .numbers(Set.of(1,2,3,4,5,6))
                .wonNumbers(Set.of(1,2,3,4))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        ResultAnnouncerDto expected = ResultAnnouncerDto.builder()
                .responseDto(responseDto)
                .message(ALREADY_CHECKED_MESSAGE.info)
                .build();
    }
}
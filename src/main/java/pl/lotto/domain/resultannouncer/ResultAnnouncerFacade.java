package pl.lotto.domain.resultannouncer;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.lotto.domain.resultannouncer.ResultMapper.*;
import static pl.lotto.domain.resultannouncer.ResultMessage.*;
@AllArgsConstructor
public class ResultAnnouncerFacade {
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResponseRepository responseRepository;
    private final Clock clock;
    @Cacheable("results")
    public ResultAnnouncerDto checkResult(String hash) {
        PlayerDto playerDto = resultCheckerFacade.findPlayerByHash(hash);
        if (playerDto == null) {
            return ResultAnnouncerDto.builder()
                    .responseDto(null)
                    .message(HASH_NOT_EXISTS_MESSAGE.info)
                    .build();
        }
        ResponseDto responseDto = mapFromPlayerDto(playerDto);
        responseRepository.save(mapFromResponseDto(responseDto));
        if (responseRepository.existsById(hash) && isBeforeDrawTime(LocalDateTime.now(clock), responseDto)) {
            return ResultAnnouncerDto.builder()
                    .responseDto(responseDto)
                    .message(WAIT_MESSAGE.info)
                    .build();
        }
        if (resultCheckerFacade.findPlayerByHash(hash).isWinner()) {
            return ResultAnnouncerDto.builder()
                    .responseDto(responseDto)
                    .message(WIN_MESSAGE.info)
                    .build();
        }
        return ResultAnnouncerDto.builder()
                .responseDto(responseDto)
                .message(LOSE_MESSAGE.info)
                .build();
    }

    public List<ResultAnnouncerDto> getAllResults() {
        return responseRepository.findAll().stream()
                .map(response -> ResultAnnouncerDto.builder()
                        .responseDto(mapResponseToDto(response).build().responseDto())
                        .message("")
                        .build())
                .collect(Collectors.toList());
    }

    private boolean isBeforeDrawTime(LocalDateTime now, ResponseDto responseDto) {
        LocalDateTime drawDate = responseDto.drawDate();
        return now.isBefore(drawDate);
    }
}

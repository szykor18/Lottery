package pl.lotto.domain.resultannouncer;

import lombok.AllArgsConstructor;
import pl.lotto.domain.resultannouncer.dto.PlayerResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import static pl.lotto.domain.resultannouncer.ResultBuilder.buildPlayerResponse;
import static pl.lotto.domain.resultannouncer.ResultBuilder.buildToPlayerResponseDto;
import static pl.lotto.domain.resultannouncer.ResultMapper.mapToPlayerResponseDto;
@AllArgsConstructor
public class ResultAnnouncerFacade {
    private final ResultCheckerFacade resultCheckerFacade;
    private final PlayerResponseRepository playerResponseRepository ;
    private final Clock clock;

    public ResultAnnouncerResponseDto checkResult(String hash) {
        if (playerResponseRepository.existsById(hash)) {
            Optional<PlayerResponse> playerByIdCached = playerResponseRepository.findById(hash);
            if (playerByIdCached.isPresent()) {
               return ResultAnnouncerResponseDto.builder()
                       .playerResponseDto(mapToPlayerResponseDto(playerByIdCached.get()))
                       .message(MessageResponse.ALREADY_CHECKED.info)
                       .build();
            }
        }
        PlayerDto playerByHash = resultCheckerFacade.findPlayerByHash(hash);
        if (playerByHash == null) {
            return ResultAnnouncerResponseDto.builder()
                    .playerResponseDto(null)
                    .message(MessageResponse.HASH_DOES_NOT_EXIST_MESSAGE.info)
                    .build();
        }
        PlayerResponseDto playerResponseDto = buildToPlayerResponseDto(playerByHash);
        playerResponseRepository.save(buildPlayerResponse(playerResponseDto, LocalDateTime.now(clock)));
        if (playerResponseRepository.existsById(hash) && isBeforeResultAnnouncementTime(playerByHash)) {
            return ResultAnnouncerResponseDto.builder()
                    .playerResponseDto(playerResponseDto)
                    .message(MessageResponse.WAIT_MESSAGE.info)
                    .build();
        }
        if (resultCheckerFacade.findPlayerByHash(hash).isWinner()) {
            return ResultAnnouncerResponseDto.builder()
                    .playerResponseDto(playerResponseDto)
                    .message(MessageResponse.WIN_MESSAGE.info)
                    .build();
        }
        return ResultAnnouncerResponseDto.builder()
                .playerResponseDto(playerResponseDto)
                .message(MessageResponse.LOSE_MESSAGE.info)
                .build();
    }
    private boolean isBeforeResultAnnouncementTime(PlayerDto playerDto) {
        LocalDateTime drawTime = playerDto.drawDate();
        return LocalDateTime.now(clock).isBefore(drawTime);
    }
}

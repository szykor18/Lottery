package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.PlayerResponseDto;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

import java.time.LocalDateTime;

class ResultBuilder {
    static PlayerResponse buildPlayerResponse(PlayerResponseDto playerResponseDto, LocalDateTime now) {
        return PlayerResponse.builder()
                .numbers(playerResponseDto.numbers())
                .hitNumbers(playerResponseDto.hitNumbers())
                .hash(playerResponseDto.hash())
                .drawDate(playerResponseDto.drawDate())
                .isWinner(playerResponseDto.isWinner())
                .createdDate(now)
                .build();
    }

    static PlayerResponseDto buildToPlayerResponseDto(PlayerDto playerByHash) {
        return PlayerResponseDto.builder()
                .numbers(playerByHash.numbers())
                .hitNumbers(playerByHash.hitNumbers())
                .hash(playerByHash.hash())
                .drawDate(playerByHash.drawDate())
                .isWinner(playerByHash.isWinner())
                .build();
    }

}

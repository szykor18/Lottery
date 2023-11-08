package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.PlayerResponseDto;

class ResultMapper {
    static PlayerResponseDto mapToPlayerResponseDto(PlayerResponse playerResponse) {
        return PlayerResponseDto.builder()
                .hash(playerResponse.hash())
                .numbers(playerResponse.numbers())
                .hitNumbers(playerResponse.hitNumbers())
                .drawDate(playerResponse.drawDate())
                .isWinner(playerResponse.isWinner())
                .build();
    }
}

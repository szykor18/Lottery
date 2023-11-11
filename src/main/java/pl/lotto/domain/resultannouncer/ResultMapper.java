package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultAnnouncerDto;
import pl.lotto.domain.resultchecker.dto.PlayerDto;

class ResultMapper {
    static ResponseDto mapFromPlayerDto(PlayerDto playerDto) {
        return ResponseDto.builder()
                .numbers(playerDto.numbers())
                .wonNumbers(playerDto.hitNumbers())
                .hash(playerDto.hash())
                .drawDate(playerDto.drawDate())
                .isWinner(playerDto.isWinner())
                .build();
    }

    static Response mapFromResponseDto(ResponseDto responseDto) {
        return Response.builder()
                .numbers(responseDto.numbers())
                .wonNumbers(responseDto.wonNumbers())
                .hash(responseDto.hash())
                .drawDate(responseDto.drawDate())
                .isWinner(responseDto.isWinner())
                .build();
    }
    static ResultAnnouncerDto.ResultAnnouncerDtoBuilder mapResponseToDto(Response response) {
        return ResultAnnouncerDto.builder()
                .responseDto(ResponseDto.builder()
                        .numbers(response.numbers())
                        .wonNumbers(response.wonNumbers())
                        .hash(response.hash())
                        .drawDate(response.drawDate())
                        .isWinner(response.isWinner())
                        .build());
    }
}

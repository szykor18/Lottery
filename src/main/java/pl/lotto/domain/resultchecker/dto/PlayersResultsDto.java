package pl.lotto.domain.resultchecker.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PlayersResultsDto(List<PlayerDto> results, String message) {
}

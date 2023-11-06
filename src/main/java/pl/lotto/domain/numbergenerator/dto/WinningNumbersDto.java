package pl.lotto.domain.numbergenerator.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
@Builder
public record WinningNumbersDto(Set<Integer> winningNumbers, LocalDateTime drawDate) {
}

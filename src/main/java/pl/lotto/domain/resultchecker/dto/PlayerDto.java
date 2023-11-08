package pl.lotto.domain.resultchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record PlayerDto(String hash,
                        Set<Integer> numbers,
                        Set<Integer> hitNumbers,
                        LocalDateTime drawDate,
                        boolean isWinner
                         ) {
}

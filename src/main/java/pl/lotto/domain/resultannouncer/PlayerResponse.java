package pl.lotto.domain.resultannouncer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record PlayerResponse(String hash,
                      Set<Integer> numbers,
                      Set<Integer> hitNumbers,
                      LocalDateTime drawDate,
                      boolean isWinner,
                      LocalDateTime createdDate) {
}

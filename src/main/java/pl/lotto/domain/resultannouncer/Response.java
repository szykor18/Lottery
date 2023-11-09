package pl.lotto.domain.resultannouncer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Response(String hash,
                       Set<Integer> numbers,
                       Set<Integer> wonNumbers,
                       LocalDateTime drawDate,
                       boolean isWinner) {
}

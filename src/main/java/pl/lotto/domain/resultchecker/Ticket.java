package pl.lotto.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
@Builder
record Ticket(String hash, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}

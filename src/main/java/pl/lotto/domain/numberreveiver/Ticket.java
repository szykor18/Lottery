package pl.lotto.domain.numberreveiver;

import java.time.LocalDateTime;
import java.util.Set;

//entity to database
record Ticket(String hash, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}

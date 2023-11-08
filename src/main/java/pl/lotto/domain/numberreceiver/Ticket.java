package pl.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.Set;

//entity to database
record Ticket(String hash, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}

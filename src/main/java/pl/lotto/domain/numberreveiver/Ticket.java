package pl.lotto.domain.numberreveiver;

import java.time.LocalDateTime;
import java.util.Set;

//entity to database
record Ticket(String ticketId, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}

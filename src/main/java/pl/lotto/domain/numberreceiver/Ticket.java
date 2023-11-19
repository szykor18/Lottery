package pl.lotto.domain.numberreceiver;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

//entity to database
@Builder
@Document("tickets")
record Ticket(String hash, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}

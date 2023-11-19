package pl.lotto.domain.resultchecker;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document("players")
record Player(String hash,
                     Set<Integer> numbers,
                     Set<Integer> hitNumbers,
                     LocalDateTime drawDate,
                     boolean isWinner) {
}

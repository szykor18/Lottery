package pl.lotto.persistence.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document("responses")
public record Response(@Id String hash,
                       Set<Integer> numbers,
                       Set<Integer> wonNumbers,
                       Set<Player> players,
                       LocalDateTime drawDate,
                       boolean isWinner) {
}

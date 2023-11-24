package pl.lotto.domain.numbergenerator;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Document
record WinningNumbers(@Id String id,
                      Set<Integer> winningNumbers,
                      LocalDateTime drawDate) { }

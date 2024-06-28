package pl.lotto.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.lotto.persistence.model.WinningNumbers;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface WinningNumbersRepository extends MongoRepository<WinningNumbers, String> {
    Optional<WinningNumbers> findWinningNumbersByDrawDate(LocalDateTime drawDate);

    boolean existsByDrawDate(LocalDateTime drawDate);
}

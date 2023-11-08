package pl.lotto.domain.resultannouncer;

import java.util.Optional;

public interface PlayerResponseRepository {
    PlayerResponse save(PlayerResponse playerResult);

    boolean existsById(String hash);

    Optional<PlayerResponse> findById(String hash);

}

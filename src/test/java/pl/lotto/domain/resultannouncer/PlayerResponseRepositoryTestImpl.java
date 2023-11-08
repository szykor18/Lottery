package pl.lotto.domain.resultannouncer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerResponseRepositoryTestImpl implements PlayerResponseRepository{
    private Map<String, PlayerResponse> playerResponses= new ConcurrentHashMap<>();
    @Override
    public PlayerResponse save(PlayerResponse playerResult) {
        playerResponses.put(playerResult.hash(), playerResult);
        return playerResult;
    }

    @Override
    public boolean existsById(String hash) {
        return playerResponses.containsKey(hash);
    }

    @Override
    public Optional<PlayerResponse> findById(String hash) {
        return Optional.ofNullable(playerResponses.get(hash));
    }
}

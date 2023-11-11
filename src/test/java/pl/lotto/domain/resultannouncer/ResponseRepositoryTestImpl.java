package pl.lotto.domain.resultannouncer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseRepositoryTestImpl implements ResponseRepository{
    private Map<String, Response> responsesDatabase = new ConcurrentHashMap<>();

    @Override
    public boolean existsById(String hash) {
        return responsesDatabase.containsKey(hash);
    }

    @Override
    public Optional<Response> findById(String hash) {
        return Optional.ofNullable(responsesDatabase.get(hash));
    }

    @Override
    public Response save(Response response) {
        responsesDatabase.put(response.hash(), response);
        return response;
    }
}

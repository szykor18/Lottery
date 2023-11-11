package pl.lotto.domain.resultannouncer;

import java.util.Optional;

public interface ResponseRepository {
    boolean existsById(String hash);
    Optional<Response> findById(String hash);

    Response save(Response response);
}

package pl.lotto.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ResponseRepository extends MongoRepository<Response, String> {
    boolean existsById(String hash);
}

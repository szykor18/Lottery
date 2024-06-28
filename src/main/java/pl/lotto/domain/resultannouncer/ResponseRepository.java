package pl.lotto.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.lotto.persistence.model.Response;;


@Repository
public interface ResponseRepository extends MongoRepository<Response, String> {
    boolean existsByHash(String hash);
}

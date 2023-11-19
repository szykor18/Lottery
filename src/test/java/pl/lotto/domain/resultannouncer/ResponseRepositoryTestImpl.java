package pl.lotto.domain.resultannouncer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ResponseRepositoryTestImpl implements ResponseRepository{
    private Map<String, Response> responsesDatabase = new ConcurrentHashMap<>();

    @Override
    public boolean existsById(String hash) {
        return responsesDatabase.containsKey(hash);
    }

    @Override
    public <S extends Response> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Response> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Response> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Response> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Response> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Response> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Response> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Response> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Response, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Response> S save(S response) {
        responsesDatabase.put(response.hash(), response);
        return response;
    }

    @Override
    public <S extends Response> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Response> findById(String hash) {
        return Optional.ofNullable(responsesDatabase.get(hash));
    }

    @Override
    public List<Response> findAll() {
        return null;
    }

    @Override
    public List<Response> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Response entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Response> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Response> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Response> findAll(Pageable pageable) {
        return null;
    }
}

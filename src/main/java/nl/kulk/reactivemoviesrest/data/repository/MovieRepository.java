package nl.kulk.reactivemoviesrest.data.repository;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
}
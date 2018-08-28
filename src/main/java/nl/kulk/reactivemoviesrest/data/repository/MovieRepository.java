package nl.kulk.reactivemoviesrest.data.repository;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * User: frisokulk
 * Date: 8/28/18
 * Time: 10:11 PM
 */
@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
}
package nl.kulk.reactivemoviesrest.data.repository;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 *
 */
@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {


    @Query()
    Flux<Movie> findByScreeningsCinemaCity(String city);


}
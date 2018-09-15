package nl.kulk.reactivemoviesrest.web.controller;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import nl.kulk.reactivemoviesrest.data.repository.MovieRepository;
import nl.kulk.reactivemoviesrest.service.movie.MovieScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 */
@RestController
@RequestMapping("/movies")
public class MovieController {



    @Autowired
    private MovieRepository movieRepository;


    @GetMapping()
    public Flux<Movie> findAll() {
        return movieRepository.findAll();
    }

    @GetMapping("/{city}")
    public Flux<Movie> findByCity(@PathVariable("city") String city) {
        return movieRepository.findByScreeningsCinemaCity(city);
    }


}
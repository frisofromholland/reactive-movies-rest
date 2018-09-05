package nl.kulk.reactivemoviesrest.web.controller;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import nl.kulk.reactivemoviesrest.data.repository.MovieRepository;
import nl.kulk.reactivemoviesrest.service.MovieScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * User: frisokulk
 * Date: 8/28/18
 * Time: 10:13 PM
 */
@RestController
@RequestMapping("/movies")
public class MovieController {


    @Autowired
    private MovieScraperService movieScraperService;

    @Autowired
    private MovieRepository movieRepository;


    @GetMapping
    public Flux<Movie> findAll() {
        movieScraperService.scrapeMovies();
        return movieRepository.findAll();
    }


}
package nl.kulk.reactivemoviesrest.service;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import org.springframework.stereotype.Service;

/**
 * User: frisokulk
 * Date: 9/5/18
 * Time: 9:45 PM
 */
@Service
public class MovieDocumentConverter {


    public Movie convert() {

        final Movie movie = new Movie();

        movie.setDescription("");
        movie.setDuration(-1);
        movie.setTitle("");
        movie.setTrailerLink("");
        movie.setYear(-1);

        return movie;
    }


}
package nl.kulk.reactivemoviesrest.service;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import nl.kulk.reactivemoviesrest.data.repository.MovieRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Date: 9/5/18
 * Time: 8:34 PM
 */
@Service
public class MovieScraperService {


    public static final String MOVIE_CONTAINER_CLASS = "city-movie";
    public static final String MOVIE_TITLE_CLASS = "movie-link";
    public static final String CINIMA_CONTAINER_CLASS = "hall";
    public static final String CINEMA_TITLE_CLASS = "cinema-link";

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieDocumentConverter movieDocumentConverter;

    //@Value("movies_url")
    private String url = "https://www.filmladder.nl/amsterdam/films";


    //@Scheduled(cron = "0 0 14 * * *")
    public void scrapeMovies() {

        Document doc = null;
        try {
            //TODO certificate in truststore: https://stackoverflow.com/questions/7744075/how-to-connect-via-https-using-jsoup
            //doc = Jsoup.connect(url).get();

            final File file = new File("/home/frisokulk/Development/playground/rest/reactive-movies-rest/src/main/resources/filmladder_amsterdam_page_20180905.txt");
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return;
        }
        final Elements movieElements = doc.getElementsByClass(MOVIE_CONTAINER_CLASS);

        final Optional<Movie> movieDocuments = movieElements
                .stream()
                .map(e -> convertToMovieDocument(e))
                .findFirst();

        //.collect(Collectors.toList());

        final Mono<Movie> movieMono = movieRepository.insert(movieDocuments.get());
        final Movie blocked = movieMono.block();

    }


    public Movie convertToMovieDocument(final Element element) {

        final Optional<String> titleElement = element.getElementsByClass(CINEMA_TITLE_CLASS).stream().findFirst().map(e -> e.text());


        final Elements cinimas = element.getElementsByClass(CINIMA_CONTAINER_CLASS);


        final Movie movie = new Movie();
        movie.setTitle(titleElement.isPresent() ? titleElement.get() : null);
        return movie;

        //return movieDocumentConverter.convert();

    }


}
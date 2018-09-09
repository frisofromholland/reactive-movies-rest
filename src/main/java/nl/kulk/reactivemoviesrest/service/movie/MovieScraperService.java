package nl.kulk.reactivemoviesrest.service.movie;

import lombok.extern.slf4j.Slf4j;
import nl.kulk.reactivemoviesrest.data.document.Movie;
import nl.kulk.reactivemoviesrest.data.repository.MovieRepository;
import nl.kulk.reactivemoviesrest.service.etl.BaseETL;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * ETL service that scrapes movies form specified url and loads them into the database
 */
@Service
@Slf4j
public class MovieScraperService extends BaseETL<Document, List<Movie>> {


    @Autowired
    private HtmlToMovieConverter htmlToMovieConverter;


    @Autowired
    private MovieRepository movieRepository;


    //TODO "https://www.filmladder.nl/amsterdam/films";
    @Value("${movies.url: }")
    private String url;


    //@Scheduled(cron = "0 0 14 * * *")
    public void scrapeMovies() {
        extractTransformLoad();
    }

    @Override
    public Document extract() {

        Document html = new Document("");
        try {

            if (StringUtils.isNotBlank(url)) {
                //TODO certificate in truststore: https://stackoverflow.com/questions/7744075/how-to-connect-via-https-using-jsoup
                html = Jsoup.connect(url).get();
            } else {
                final File file = new File("/home/frisokulk/Development/playground/rest/reactive-movies-rest/src/main/resources/filmladder_amsterdam_page_20180905.txt");
                html = Jsoup.parse(file, "UTF-8");
            }
        } catch (IOException e) {
            log.error("Error collecting movie data", e);
        }

        return html;
    }

    @Override
    public List<Movie> transform(Document extractedData) {
        return htmlToMovieConverter.convert(extractedData);
    }

    @Override
    public void load(List<Movie> transformedData) {
        final Flux<Movie> movieFlux = movieRepository.insert(transformedData);
        movieFlux.blockLast();
    }


}
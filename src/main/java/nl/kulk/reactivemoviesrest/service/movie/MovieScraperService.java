package nl.kulk.reactivemoviesrest.service.movie;

import lombok.extern.slf4j.Slf4j;
import nl.kulk.reactivemoviesrest.data.document.Movie;
import nl.kulk.reactivemoviesrest.data.repository.MovieRepository;
import nl.kulk.reactivemoviesrest.service.etl.BaseETL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * ETL service that scrapes movies form specified baseUrl and loads them into the database
 */
@Service
@Slf4j
public class MovieScraperService extends BaseETL<String, Document, List<Movie>> {


    @Autowired
    private HtmlToMovieConverter htmlToMovieConverter;
    @Autowired
    private MovieRepository movieRepository;

    private static final String baseUrl = "https://www.filmladder.nl/";
    private static final List<String> cities = Arrays.asList(new String[]{"amsterdam", "rotterdam", "utrecht"});


    public void scrapeMovies() {
        //First delete old documents
        movieRepository.findAll().flatMap(m -> movieRepository.delete(m)).blockLast();
        //Then insert new ones
        for (String city : cities) {
            extractTransformLoad(city);
        }
    }

    @Override
    public Document extract(final String city) {

        Document html = new Document("");

        try {
            html = Jsoup.connect(baseUrl + city + "/films").get();
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
        movieRepository.insert(transformedData).blockLast();
    }


}
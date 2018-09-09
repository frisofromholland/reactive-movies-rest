package nl.kulk.reactivemoviesrest.service.movie;

import nl.kulk.reactivemoviesrest.data.document.Cinema;
import nl.kulk.reactivemoviesrest.data.document.Movie;
import nl.kulk.reactivemoviesrest.data.document.Screening;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class to convert html document to a list of Movie documents
 */
@Service
public class HtmlToMovieConverter {

    public static final String MOVIE_CONTAINER_CLASS = "city-movie";
    public static final String MOVIE_TITLE_CLASS = "movie-link";
    public static final String CINIMA_CONTAINER_CLASS = "hall-container";
    public static final String CINEMA_TITLE_CLASS = "cinema-link";


    public List<Movie> convert(final Document htmlDocument) {

        final Elements singleMovieElements = htmlDocument.getElementsByClass(MOVIE_CONTAINER_CLASS);

        final List<Movie> movieDocuments = singleMovieElements
                .stream()
                .map(el -> convertToMovieDocument(el))
                .collect(Collectors.toList());

        return movieDocuments;
    }


    private Movie convertToMovieDocument(final Element singleMovieElement) {


        final Movie movie = convertMovie(singleMovieElement);
        movie.setScreenings(convertScreenings(singleMovieElement));


        return movie;

    }

    private Movie convertMovie(final Element singleMovieElement) {

        final Movie movie = new Movie();

        final Optional<String> titleElement = singleMovieElement.getElementsByClass(CINEMA_TITLE_CLASS)
                .stream()
                .map(el -> el.text())
                .findFirst();
        movie.setTitle(titleElement.isPresent() ? titleElement.get() : null);

        return movie;
    }

    private List<Screening> convertScreenings(final Element singleMovieElement) {

        final List<Screening> screenings = new ArrayList<>();

        final Elements venueAndScreeningsElements = singleMovieElement.getElementsByClass(CINIMA_CONTAINER_CLASS);

        //TODO This is not the cinema's name yet, but the movie title
        final Optional<String> cinemaOptional = singleMovieElement.getElementsByClass(CINEMA_TITLE_CLASS)
                .stream()
                .map(el -> el.text())
                .findFirst();


        if (cinemaOptional.isPresent()) {
            final Cinema venue = new Cinema();
            venue.setName(cinemaOptional.get());
            for (Element venueAndScreeningsElement : venueAndScreeningsElements) {
                screenings.addAll(convertScreeningForCinema(venueAndScreeningsElement, venue));
            }
        }

        return screenings;
    }

    private List<Screening> convertScreeningForCinema(final Element singleVenueAndScreeningsElement, final Cinema venue) {

        final Screening screening = new Screening();
        screening.setCinema(venue);

        final List<Screening> screenings = singleVenueAndScreeningsElement.getElementsByAttributeValue("itemprop","startDate")
                .stream()
                .map(el -> el.attr("content"))
                .map(st -> buildScreening(st, venue))
                .filter(sc -> sc != null)
                .collect(Collectors.toList());

        return screenings;
    }

    private Screening buildScreening(final String startDateTimestring, final Cinema venue) {

        Screening screening = null;

        if (startDateTimestring != null && startDateTimestring.contains("T") && startDateTimestring.contains("+")) {
            screening = new Screening();
            screening.setCinema(venue);
           screening.setStartDateTime(startDateTimestring);
        }

        return screening;

    }


}
package nl.kulk.reactivemoviesrest.service;

import nl.kulk.reactivemoviesrest.data.document.Movie;
import nl.kulk.reactivemoviesrest.data.document.Screening;
import nl.kulk.reactivemoviesrest.service.movie.HtmlToMovieConverter;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.core.Is;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class HtmlToMovieConverterTest {


    @InjectMocks
    private HtmlToMovieConverter htmlToMovieConverter;


    @Before
    public void intit() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldConvertHtmlToMovieList() throws IOException {

        final Document html = Jsoup.parse(new File("/home/frisokulk/Development/playground/rest/reactive-movies-rest/src/main/resources/filmladder_amsterdam_page_20180905.txt"), "UTF-8");

        final List<Movie> result = htmlToMovieConverter.convert(html);

        Assert.assertThat(result.size(), Is.is(118));
    }


    @Test
    public void alleConvertedShouldUnique() throws IOException {

        final Document html = Jsoup.parse(new File("/home/frisokulk/Development/playground/rest/reactive-movies-rest/src/main/resources/filmladder_amsterdam_page_20180905.txt"), "UTF-8");

        final List<Movie> result = htmlToMovieConverter.convert(html);

        for (Movie movie : result) {

            final List<Screening> screenings = movie.getScreenings();

            for (int i = 0; i < screenings.size() - 1; i++) {
                final Screening screening = screenings.get(i);
                final Screening nextScreening = screenings.get(i + 1);
                Assert.assertFalse(
                        StringUtils.equals(screening.getStartDateTime(), nextScreening.getStartDateTime())
                                && StringUtils.equals(screening.getCinema().getName(), nextScreening.getCinema().getName())
                );
            }
        }

    }


}
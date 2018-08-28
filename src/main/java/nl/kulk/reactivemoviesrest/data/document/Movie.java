package nl.kulk.reactivemoviesrest.data.document;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * User: frisokulk
 * Date: 8/25/18
 * Time: 9:52 PM
 */
@Document(collection = "movie")
@Getter
@Setter
public class Movie {

    @Id
    private String id;

    private String title;

    private String description;

    private int duration;

    private int year;

    private String trailerLink;

    private Screening[] screenings;


}
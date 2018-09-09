package nl.kulk.reactivemoviesrest.data.document;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 *
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

    private List<Screening> screenings;


}
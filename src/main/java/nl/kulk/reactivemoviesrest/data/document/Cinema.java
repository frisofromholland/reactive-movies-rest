package nl.kulk.reactivemoviesrest.data.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 *
 */
@Getter
@Setter
public class Cinema {

    private String name;

    private String city;

}
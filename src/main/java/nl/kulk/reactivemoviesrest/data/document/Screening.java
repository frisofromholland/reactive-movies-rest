package nl.kulk.reactivemoviesrest.data.document;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
public class Screening {

    private Cinema cinema;

    private String startDateTime;
}
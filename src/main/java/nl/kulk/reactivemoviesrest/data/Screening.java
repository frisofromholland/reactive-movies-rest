package nl.kulk.reactivemoviesrest.data;

import lombok.Getter;
import lombok.Setter;

/**
 * User: frisokulk
 * Date: 8/25/18
 * Time: 10:39 PM
 */
@Getter
@Setter
public class Screening {

    private Cinema cinema;

    private String day;

    private String startTime;

    private String endTime;
}
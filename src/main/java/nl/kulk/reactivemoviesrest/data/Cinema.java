package nl.kulk.reactivemoviesrest.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * User: frisokulk
 * Date: 8/25/18
 * Time: 10:35 PM
 */
@Getter
@Setter
public class Cinema {

    private String Name;

    private String City;

}
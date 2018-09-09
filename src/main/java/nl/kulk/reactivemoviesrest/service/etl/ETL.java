package nl.kulk.reactivemoviesrest.service.etl;

/**
 * Interface for implementations of Extract/Transform/Load pattern
 */
public interface ETL<E, T> {


    E extract();

    T transform(E extractedData);

    void load(T transformedData);

}
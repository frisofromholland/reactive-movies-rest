package nl.kulk.reactivemoviesrest.service.etl;

/**
 * Interface for implementations of Extract/Transform/Load pattern
 */
public interface ETL<I,E, T> {


    E extract(I input);

    T transform(E extractedData);

    void load(T transformedData);

}
package nl.kulk.reactivemoviesrest.service.etl;

/**
 * Base implementations of Extract/Transform/Load pattern
 */
public abstract class BaseETL<I, E, T> implements ETL<I,E, T> {


    public final void extractTransformLoad(final I input) {

        final E e = extract(input);
        final T t = transform(e);
        load(t);
    }

}
package nl.kulk.reactivemoviesrest.service.etl;

/**
 * Base implementations of Extract/Transform/Load pattern
 */
public abstract class BaseETL<E, T> implements ETL<E, T> {


    public final void extractTransformLoad() {

        final E e = extract();
        final T t = transform(e);
        load(t);
    }

}
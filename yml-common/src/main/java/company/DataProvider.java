package company;

/**
 * The interface describes objects that able provide some data
 */
public interface DataProvider<T> {

    T provide();
}

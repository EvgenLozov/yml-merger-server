package company.config;

/**
 * Created by Naya on 10.04.2016.
 */
public interface Validator<T> {
    void validate(T t);
}

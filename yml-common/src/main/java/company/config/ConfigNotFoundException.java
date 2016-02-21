package company.config;

/**
 * Created by Naya on 21.02.2016.
 */
public class ConfigNotFoundException extends RuntimeException {
    public ConfigNotFoundException(String message) {
        super(message);
    }
}

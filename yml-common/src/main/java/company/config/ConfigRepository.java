package company.config;

import java.util.List;

public interface ConfigRepository<T extends Config> {

    List<T> list();

    void save(T config);

    T create(T config);

    void delete(String id);

    T get(String id);
}

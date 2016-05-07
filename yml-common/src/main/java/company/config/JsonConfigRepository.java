package company.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class JsonConfigRepository<T extends Config> implements ConfigRepository<T> {

    private String configFileName;
    private Class<T> configClass;
    private ObjectMapper objectMapper;

    public JsonConfigRepository(String configFileName, Class<T> configClass, ObjectMapper objectMapper) {
        this.configFileName = configFileName;
        this.configClass = configClass;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<T> list() {
        try(InputStream inputStream = new FileInputStream(configFileName)) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, configClass);

            return objectMapper.readValue(inputStream, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read " + configFileName);
        }
    }

    @Override
    public void save(T modifiedConfig) {
        List<T> configList = list();

        int index = -1;

        for (T config : configList) {
            if ( modifiedConfig.getId().equals(config.getId()))
                index = configList.indexOf(config);
        }

        if (index < 0)
            return;

        configList.set(index, modifiedConfig);
        updateStorageFile(configList);
    }

    @Override
    public T create(T config) {
        String id = UUID.randomUUID().toString();
        config.setId(id);

        List<T> configList = list();
        configList.add(config);

        updateStorageFile(configList);

        return config;
    }

    @Override
    public void delete(String id) {
        List<T> configList = list();
        Iterator<T> iterator = configList.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getId().equals(id))
                iterator.remove();
        }

        updateStorageFile(configList);
    }

    @Override
    public T get(String id) {
        for (T config : list()) {
            if (config.getId().equals(id))
                return config;
        }

        throw new ConfigNotFoundException("Unable to find config by id");
    }

    private void updateStorageFile(List<T> configList) {
        File configFile = new File(configFileName);
        if (!configFile.exists())
            try {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Unable to create " + configFileName);

            }

        try {
            objectMapper.writeValue(configFile, configList);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write " + configFileName);
        }
    }
}

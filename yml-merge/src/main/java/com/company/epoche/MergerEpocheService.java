package com.company.epoche;

import com.company.config.ConfigGroup;
import com.company.config.MergerConfig;
import company.config.ConfigRepository;
import company.epoche.EpocheSupplier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Yevhen
 */
public class MergerEpocheService {

    private ConfigRepository<ConfigGroup> configGroupRepository;
    private ConfigRepository<MergerConfig> mergerConfigRepository;

    public MergerEpocheService(ConfigRepository<ConfigGroup> configGroupRepository,
                               ConfigRepository<MergerConfig> mergerConfigRepository) {
        this.configGroupRepository = configGroupRepository;
        this.mergerConfigRepository = mergerConfigRepository;
    }

    public File get(String configGroupId, String currency){
        ConfigGroup configGroup = configGroupRepository.get(configGroupId);

        List<Supplier<File>> suppliers = new ArrayList<>();
        for (String configId : configGroup.getMergerConfigIds()) {
            MergerConfig config = mergerConfigRepository.get(configId);

            String outputFileDir = config.getOutputFile();
            File outputDir = new File(outputFileDir);
            for (File file : outputDir.listFiles()) {
                if (file.getName().contains(currency))
                    suppliers.add(() -> file);
            }
        }

        return new EpocheSupplier<>(suppliers, configGroup.getEpocheStart(), configGroup.getEpochePeriod()).get();
    }
}

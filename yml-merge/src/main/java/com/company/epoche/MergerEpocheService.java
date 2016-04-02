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

    public File get(String configGroupId){
        ConfigGroup configGroup = configGroupRepository.get(configGroupId);

        List<Supplier<File>> suppliers = new ArrayList<>();
        for (String configId : configGroup.getMergerConfigIds()) {
            MergerConfig config = mergerConfigRepository.get(configId);
            String outputFileName = config.getOutputFile();
            File outputFile = new File(outputFileName);
            if (!outputFile.exists())
                throw new RuntimeException("Result file does not exists for config : " + config.getName());

            suppliers.add(() -> outputFile);
        }

        return new EpocheSupplier<>(suppliers, configGroup.getEpocheStart(), configGroup.getEpochePeriod()).get();
    }
}

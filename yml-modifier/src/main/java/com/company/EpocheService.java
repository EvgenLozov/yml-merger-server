package com.company;

import com.company.exception.ModifierException;
import company.config.ConfigRepository;
import company.epoche.EpocheSupplier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EpocheService {

    private ConfigRepository<ModifierConfig> configRepository;

    public EpocheService(ConfigRepository<ModifierConfig> configRepository) {
        this.configRepository = configRepository;
    }

    public File get(String configId){
        ModifierConfig config = configRepository.get(configId);

        if(config.getEpocheStart() == 0 ||
            config.getEpochePeriod() == 0)
                throw new ModifierException("Process has never been started/finished or epochePeriod is not set!");

        File outputDir = new File(config.getOutputDir());

        List<Supplier<File>> suppliers = new ArrayList<>();
        for (File file : outputDir.listFiles()) {
            suppliers.add(() -> file);
        }

        return new EpocheSupplier<>(suppliers, config.getEpocheStart(), config.getEpochePeriod()).get();
    }
}

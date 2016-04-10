package com.company;

import com.company.exception.ModifierException;
import company.config.Validator;

/**
 * Created by Naya on 10.04.2016.
 */
public class ModifierConfigValidator implements Validator<ModifierConfig> {
    public void validate(ModifierConfig config){

        if (config.getLimitSize()== 0 && config.getFilesCount() == 0)
            throw new ModifierException("Limit size or files count is required");
        if (config.getLimitSize()> 0 && config.getFilesCount() > 0)
            throw new ModifierException("One parameter (limit size OR files count) should be set ");
        if (config.getLimitSize()== 0 && config.getFilesCount() < 0)
            throw new ModifierException("Files count is a positive integer ");
        if ((config.getLimitSize()< 0 || config.getLimitSize() < 8192 ) && config.getFilesCount() == 0)
            throw new ModifierException("Limit size is a positive integer greater then 8192 bytes");
        if ((config.getInputFile().isEmpty() || config.getInputFile() == null) &&
                (config.getInputFileURL().isEmpty() || config.getInputFileURL() == null))
            throw new ModifierException("InputFile or InputFileURL is required");
    }
}
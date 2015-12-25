package com.company;

import java.io.*;
import java.util.Properties;

/**
 * Created by user50 on 27.06.2015.
 */
public class ModifierConfigProvider {

    public ModifierConfig get(){
        return new ModifierConfig(getProperties());
    }

    private Properties getProperties(){
        File properyFile = new File("config.properties");
        if (!properyFile.exists())
        {
            System.out.println("Unable to find config file "+properyFile.getAbsolutePath());
            System.out.println("Loading default config");
            return getDefault();
        }

        Properties prop = new Properties();
        try(InputStream input = new FileInputStream(properyFile)) {
            prop.load(input);
        }  catch (FileNotFoundException ex) {
            throw new RuntimeException("Unable to find config.properties file");
        } catch (IOException e) {
            throw new RuntimeException("Unable to read config.properties file. " + e.getMessage());
        }

        return prop;
    }

    private Properties getDefault()
    {
        Properties prop = new Properties();

        try (InputStream input = ModifierConfigProvider.class.getResourceAsStream("/config.properties")) {
            prop.load(input);
        }
        catch ( IOException ex) {
            throw new RuntimeException(ex);
        }

        return prop;
    }
}

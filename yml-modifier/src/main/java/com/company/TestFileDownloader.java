package com.company;

import com.company.scheduler.JobKeyFactory;
import com.company.scheduler.TriggerFactory;
import com.company.scheduler.TriggerFactoryPerHours;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import company.config.JsonBasedConfigRepository;

import java.io.*;


/**
 * Created by Naya on 19.01.2016.
 */
public class TestFileDownloader {
    public static void main(String[] args) throws IOException {
        // FileDownloader fd = new FileDownloader("http://bilasad.com/pricelist.xml","d:/java/prices");
       // fd.download();

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-HH;mm");
        System.out.println("/priseFromURL" + sdf.format(new Date()));


        File file = new File("d:/java/prices/df.xml");
        FileWriter out = new FileWriter(file);
        out.write("llalala naayadaa");
        out.close();*/
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);


        /*ModifierConfig config = new JsonBasedConfigRepository<>("config/config.json",ModifierConfig.class,mapper).get("id");
        TriggerFactoryPerHours tf = new TriggerFactoryPerHours(new JobKeyFactory());
        tf.get(config);
*/
    }
}

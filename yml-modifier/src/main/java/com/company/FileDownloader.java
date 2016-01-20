package com.company;

import java.io.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Naya on 19.01.2016.
 */
public class FileDownloader {
    private String inputFileURL;
    private String outputDir;
    private String downloadedFile;

    public FileDownloader(String inputFileURL, String outputDir) {
        this.inputFileURL = inputFileURL;
        this.outputDir = outputDir;
    }

    public String download() throws IOException {
        File file = new File(getDownloadedFile());
        URL url = new URL(inputFileURL);
        InputStream in = url.openStream();
        FileOutputStream out = new FileOutputStream(file);
        int bytesRead = -1;
        byte[] outputByte = new byte[1024];
        while ((bytesRead = in.read(outputByte, 0, 1024)) != -1) {
            out.write(outputByte, 0, bytesRead);
        }
            out.flush();
            out.close();
            in.close();

            return downloadedFile;
    }

    private String getDownloadedFile(){
        SimpleDateFormat sdf = new SimpleDateFormat("-dd-MM-HH-mm");
        return downloadedFile = outputDir+"/downloadedFromURL"+sdf.format(new Date())+".xml";

    }
}

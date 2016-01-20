package com.company;

import java.io.*;


/**
 * Created by Naya on 19.01.2016.
 */
public class TestFileDownloader {
    public static void main(String[] args) throws IOException {
        FileDownloader fd = new FileDownloader("http://bilasad.com/pricelist.xml","d:/java/prices");
        fd.download();

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-HH;mm");
        System.out.println("/priseFromURL" + sdf.format(new Date()));


        File file = new File("d:/java/prices/df.xml");
        FileWriter out = new FileWriter(file);
        out.write("llalala naayadaa");
        out.close();*/
    }
}

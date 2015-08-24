package com.company.processing;

import com.company.config.CategoryIdsPair;
import com.company.config.Replace;
import company.bytearray.ByteArrayProcessor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ReplaceProcessing implements ByteArrayProcessor {

    String encoding;
    List<Replace> replaces;

    public ReplaceProcessing(String encoding, List<Replace> replaces) {
        this.encoding = encoding;
        this.replaces = replaces;
    }

    @Override
    public byte[] process(byte[] bytes) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        try(Scanner scanner = new Scanner(new ByteArrayInputStream(bytes), encoding))
        {
            while (scanner.hasNextLine())
            {

                String line = scanner.nextLine();

                if (line == null) {
                    System.out.println("fuck up");
                }

                if (scanner.hasNextLine())
                    printWriter.println(performReplacing(line));
                else
                    printWriter.print(performReplacing(line));
            }
        }

        printWriter.close();

        return outputStream.toByteArray();
    }

    private String performReplacing(String line)
    {
        String result = line;
        for (Replace replace : replaces)
            for (String word : replace.getWordsToReplace()) {
                result = result.replaceAll(word, replace.getReplacement());
            }

        return result;
    }
}

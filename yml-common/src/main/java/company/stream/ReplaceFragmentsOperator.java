package company.stream;

import company.domain.Replace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ReplaceFragmentsOperator implements InputStreamOperator {

    String encoding;
    List<Replace> replaces;

    public ReplaceFragmentsOperator(String encoding, List<Replace> replaces) {
        this.encoding = encoding;
        this.replaces = replaces;
    }

    @Override
    public InputStream apply(InputStream inputStream){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        try(Scanner scanner = new Scanner(inputStream, encoding))
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

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private String performReplacing(String line)
    {
        String result = line;
        for (Replace replace : replaces)
            for (String word : replace.getWordsToReplace()) {
                result = result.replaceAll(word, replace.getReplacement());
                result = result.replaceAll(firstCharToUpperCase(word), replace.getReplacement());
                result = result.replaceAll(word.toUpperCase(), replace.getReplacement());
            }

        return result;
    }

    private String firstCharToUpperCase(String line)
    {
        char first = Character.toUpperCase(line.charAt(0));
        return first + line.substring(1);
    }
}

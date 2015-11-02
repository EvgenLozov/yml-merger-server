package com.company.readerproviders;

import com.company.http.HttpResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by user50 on 28.07.2015.
 */
public class SaveIntoFileHttpResponseHandler implements HttpResponseHandler<String> {

    String encoding;

    public SaveIntoFileHttpResponseHandler(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String handle(CloseableHttpResponse httpResponse) {
        String fileName = "tmp/" + UUID.randomUUID().toString();

        try {
            Scanner scanner = new Scanner(httpResponse.getEntity().getContent(), encoding);
            PrintWriter output = new PrintWriter(fileName);

            while (scanner.hasNextLine())
                output.println(scanner.nextLine());

            output.close();
            scanner.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }
}

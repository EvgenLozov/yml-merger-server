package company.http;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.UUID;

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
        if (!httpResponse.getHeaders("content-type")[0].getValue().contains("application/octet-stream"))
            throw new RuntimeException("Content-type must be application/octet-stream");

        String fileName = "tmp" + File.separator + UUID.randomUUID().toString();

        try {
            Scanner scanner = new Scanner(httpResponse.getEntity().getContent(), encoding);
            PrintWriter output = new PrintWriter(fileName);

            while (scanner.hasNextLine())
                output.println(scanner.nextLine());

            output.close();
            scanner.close();

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

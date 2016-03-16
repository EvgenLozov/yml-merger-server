package company.http;

import org.apache.http.client.methods.CloseableHttpResponse;

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
        String fileName = "D:\\Java\\yml-merger-server\\yml-merger-server\\yml-modifier-web\\tmp" + UUID.randomUUID().toString(); //tmp!!!

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

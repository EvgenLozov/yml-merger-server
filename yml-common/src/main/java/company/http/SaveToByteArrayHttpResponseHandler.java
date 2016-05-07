package company.http;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SaveToByteArrayHttpResponseHandler implements HttpResponseHandler<byte[]> {
    @Override
    public byte[] handle(CloseableHttpResponse httpResponse) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            httpResponse.getEntity().writeTo(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

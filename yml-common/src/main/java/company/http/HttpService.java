package company.http;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * Created by user50 on 26.05.2015.
 */
public class HttpService {

    CloseableHttpClient httpClient;

    public HttpService(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public <T> T execute(HttpRequestProvider httpRequestProvider, HttpResponseHandler<T> responseHandler) throws IOException {
        HttpRequestBase httpRequest = httpRequestProvider.getRequest();

        try (CloseableHttpResponse response = httpClient.execute(new HttpHost(httpRequestProvider.getHost()), httpRequest)) {

            return responseHandler.handle(response);
        }
    }
}

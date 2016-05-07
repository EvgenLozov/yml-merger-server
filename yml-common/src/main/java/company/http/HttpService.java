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

    public <T> T execute(HttpRequestProvider httpRequestProvider, HttpResponseHandler<T> responseHandler, long sleep, int maxTries) throws IOException {
        int tryCount = 0;

        while (++tryCount < maxTries - 1)
        {
            try {
                return execute(httpRequestProvider, responseHandler);
            } catch (Exception e) {
                sleep(sleep);
            }
        }

        return execute(httpRequestProvider, responseHandler);//last try

    }

    private void sleep(long sleep)
    {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

package company.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by user50 on 21.06.2015.
 */
public class DownloadPriceListRequest implements HttpRequestProvider {

    String url;

    public DownloadPriceListRequest(String url) {
        this.url = url;
    }

    @Override
    public HttpRequestBase getRequest() {
        return new HttpGet(url);
    }

    @Override
    public String getHost() {
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

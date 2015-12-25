import com.company.allowedcategories.Category;
import com.company.config.MergerConfig;
import com.company.config.ConfigProvider;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.readerproviders.DownloadPriceListRequest;
import com.company.readerproviders.ExtractCategory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import java.util.Base64;
import java.util.Set;

public class CategoryProviderTest {

    @Test
    public void testName() throws Exception {
        MergerConfig config = new ConfigProvider().get();
        String psw = new String(Base64.getDecoder().decode(config.getPsw().getBytes()));
        CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), psw).get();


        HttpService httpService = new HttpService(httpClient);

        for (String url : config.getUrls()) {
            long start = System.currentTimeMillis();
            Set<Category> categories  = httpService.execute(new DownloadPriceListRequest(url), new ExtractCategory(config.getEncoding()));
            System.out.println(System.currentTimeMillis() - start);
        }


    }

}

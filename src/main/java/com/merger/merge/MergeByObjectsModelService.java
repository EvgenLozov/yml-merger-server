package com.merger.merge;

import com.merger.Config;
import com.merger.Util;
import com.merger.http.HttpService;
import com.merger.main.DownloadPriceListRequest;
import com.merger.main.FileDownloadHandler;
import com.merger.yml.Offer;
import com.merger.yml.YmlCatalog;
import com.merger.yml.YmlCategory;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by user50 on 21.06.2015.
 */
public class MergeByObjectsModelService implements MergeService{

    private Config config;

    public MergeByObjectsModelService(Config config) {
        this.config = config;
    }

    public byte[] getYml()
    {
        CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), config.getPsw()).get();
        HttpService httpService = new HttpService(httpClient);

        Iterator<String> iterator = config.getUrls().iterator();

        if (!iterator.hasNext()) {
            throw new RuntimeException("Must be specified at least one price url");
        }


        try {
            YmlCatalog ymlCatalog = httpService.execute(new DownloadPriceListRequest(iterator.next()), new FileDownloadHandler(config.getEncoding()));

            while (iterator.hasNext())
            {
                YmlCatalog nextYmlCatalog = httpService.execute(new DownloadPriceListRequest(iterator.next()), new FileDownloadHandler(config.getEncoding()));

                merge(ymlCatalog, nextYmlCatalog);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Util.marshal(ymlCatalog, outputStream, config.getEncoding());

            return outputStream.toByteArray();
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private void merge(YmlCatalog toCatalog, YmlCatalog fromCatalog)
    {
        Set<YmlCategory> categories = new HashSet<>(toCatalog.shop.categories);
        categories.addAll(fromCatalog.shop.categories);
        toCatalog.shop.categories = new ArrayList<>(categories);

        Set<Offer> offers = new HashSet<>(toCatalog.shop.offers);
        offers.addAll(fromCatalog.shop.offers);
        toCatalog.shop.offers = new ArrayList<>(offers);
    }
}

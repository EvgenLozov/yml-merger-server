package com.merger.merge;

import com.merger.Config;
import com.merger.Util;
import com.merger.http.HttpService;
import com.merger.main.DownloadPriceListRequest;
import com.merger.main.FileDownloadAsDOMHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lozov on 23.06.15.
 */
public class MergeByDOMService implements MergeService {

    private Config config;

    public MergeByDOMService(Config config) {
        this.config = config;
    }

    @Override
    public byte[] getYml() {
        CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), config.getPsw()).get();
        HttpService httpService = new HttpService(httpClient);

        Iterator<String> iterator = config.getUrls().iterator();

        if (!iterator.hasNext()) {
            throw new RuntimeException("Must be specified at least one price url");
        }

        try {
            Document document = httpService.execute(new DownloadPriceListRequest(iterator.next()), new FileDownloadAsDOMHandler(config.getEncoding()));

            while (iterator.hasNext()){
                Document nextDocument = httpService.execute(new DownloadPriceListRequest(iterator.next()), new FileDownloadAsDOMHandler(config.getEncoding()));

                mergeCategories(document, nextDocument);
                mergeOffers(document, nextDocument);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Util.writeDocument(document, bos, config.getEncoding());

            return bos.toByteArray();

        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private void mergeCategories(Document toDocument, Document fromDocument) {
        NodeList toCategories = toDocument.getElementsByTagName("category");

        Set<String> categoryIds = new HashSet<>();

        for (int i = 0; i < toCategories.getLength(); i++) {
            Element categoryEl = (Element) toCategories.item(i);
            String categoryId = categoryEl.getAttribute("id");
            categoryIds.add(categoryId);
        }

        Element toCategoriesEl = (Element) toDocument.getElementsByTagName("categories").item(0);

        NodeList fromCategories = fromDocument.getElementsByTagName("category");

        for (int i = 0; i < fromCategories.getLength(); i++) {
            Element categoryEl = (Element) fromCategories.item(i);
            if (categoryIds.contains(categoryEl.getAttribute("id")))
                continue;
            moveNode(categoryEl, toDocument, toCategoriesEl);
        }
    }

    private void mergeOffers(Document toDocument, Document fromDocument) {
        Element toOffersEl = (Element) toDocument.getElementsByTagName("offers").item(0);
        NodeList fromOffers = fromDocument.getElementsByTagName("offer");

        for (int i = 0; i < fromOffers.getLength(); i++) {
            Node offerNode = fromOffers.item(i);
            moveNode((Element)offerNode, toDocument, toOffersEl);
        }
    }

    private void moveNode(Element element, Document toDocument, Element newParent) {
        Node copiedCategoryNode = element.cloneNode(true);
        toDocument.adoptNode(copiedCategoryNode);
        newParent.appendChild(copiedCategoryNode);
    }
}

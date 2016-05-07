package com.company;

import company.http.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ModifierXmlBytesProvider {

    ModifierConfig config;

    public ModifierXmlBytesProvider(ModifierConfig config) {
        this.config = config;
    }

    public byte[] get()
    {
        try {
            return config.getInputFileURL() !=null && !config.getInputFileURL().isEmpty() ?
                    downloadFileAndProvide():
                    loadBytesFromFile();
        } catch (IOException e) {
            throw new RuntimeException("Unable to provide xml input stream", e);
        }
    }

    private byte[] downloadFileAndProvide() throws IOException {
        CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), config.getPsw()).get();
        HttpService httpService = new HttpService(httpClient);
        HttpRequestProvider requestProvider = new DownloadPriceListRequest(config.getInputFileURL());
        HttpResponseHandler<byte[]> responseHandler = new SaveToByteArrayHttpResponseHandler();

        return httpService.execute(requestProvider, responseHandler);
    }

    private byte[] loadBytesFromFile()
    {
        try(InputStream inputStream = new FileInputStream(config.getInputFile())){
            return IOUtils.toByteArray(inputStream);
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }
    }

}

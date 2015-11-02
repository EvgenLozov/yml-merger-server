package com.company.service;

import com.company.config.Config;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * @author Yevhen
 */
public interface MergeService {
    public void process(Config config) throws IOException, XMLStreamException;
}

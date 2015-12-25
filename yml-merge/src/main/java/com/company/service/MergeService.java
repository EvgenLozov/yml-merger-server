package com.company.service;

import com.company.config.MergerConfig;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * @author Yevhen
 */
public interface MergeService {
    public void process(MergerConfig config) throws IOException, XMLStreamException;
}

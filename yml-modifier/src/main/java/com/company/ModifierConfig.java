package com.company;


import company.config.Config;
import company.config.PswConfig;
import company.domain.Replace;

import java.util.List;

public class ModifierConfig implements Config, PswConfig {
    private String id;
    private String inputFileURL;
    private String inputFile;
    private String outputDir;
    private String encoding;
    private boolean modifyDescription;
    private boolean modifyOfferId;
    private String offerIdPrefix;
    private boolean modifyCategoryId;
    private String categoryIdPrefix;
    private String removedCategoryId;
    private String template;
    private int filesCount;
    private String time;
    private int period;
    private String user;
    private String psw;
    private String name;
    private long limitSize;
    private long epocheStart;
    private long epochePeriod;
    private List<Replace> replaces;


    public long getEpocheStart() {
        return epocheStart;
    }

    public void setEpocheStart(long epocheStart) {
        this.epocheStart = epocheStart;
    }

    public long getEpochePeriod() {
        return epochePeriod;
    }

    public void setEpochePeriod(long epochePeriod) {
        this.epochePeriod = epochePeriod;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getInputFileURL() {
        return inputFileURL;
    }

    public void setInputFileURL(String inputFileURL) {
        this.inputFileURL = inputFileURL;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isModifyDescription() {
        return modifyDescription;
    }

    public void setModifyDescription(boolean modifyDescription) {
        this.modifyDescription = modifyDescription;
    }

    public boolean isModifyOfferId() {
        return modifyOfferId;
    }

    public void setModifyOfferId(boolean modifyOfferId) {
        this.modifyOfferId = modifyOfferId;
    }

    public String getOfferIdPrefix() {
        return offerIdPrefix;
    }

    public void setOfferIdPrefix(String offerIdPrefix) {
        this.offerIdPrefix = offerIdPrefix;
    }

    public boolean isModifyCategoryId() {
        return modifyCategoryId;
    }

    public void setModifyCategoryId(boolean modifyCategoryId) {
        this.modifyCategoryId = modifyCategoryId;
    }

    public String getCategoryIdPrefix() {
        return categoryIdPrefix;
    }

    public void setCategoryIdPrefix(String categoryIdPrefix) {
        this.categoryIdPrefix = categoryIdPrefix;
    }

    public String getRemovedCategoryId() {
        return removedCategoryId;
    }

    public void setRemovedCategoryId(String removedCategoryId) {
        this.removedCategoryId = removedCategoryId;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(int filesCount) {
        if (filesCount <= 0)
            throw new IllegalArgumentException("filesCount has to be a positive integer");
        this.filesCount = filesCount;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPeriod() {

        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Replace> getReplaces() {
        return replaces;
    }

    public void setReplaces(List<Replace> replaces) {
        this.replaces = replaces;
    }

    public long getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(long limitSize) {
        this.limitSize = limitSize;
    }
}

package com.company.config;

import company.Currency;

import java.util.List;

/**
 * Created by user50 on 21.06.2015.
 */
public class Config {

    private String user;
    private String psw;
    private List<String> urls;
    private List<String> files;
    private String encoding;
    private String outputFile;
    private List<String> categoryIds;
    private Currency currency;
    private List<Replace> replaces;
    private double oldPrice;

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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Replace> getReplaces() {
        return replaces;
    }

    public void setReplaces(List<Replace> replaces) {
        this.replaces = replaces;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }
}

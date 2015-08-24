package com.company.config;

import java.util.Set;

/**
 * Created by user50 on 26.07.2015.
 */
public class Replace {

    private Set<String> wordsToReplace;
    private String replacement;

    public Replace() {
    }

    public Replace(Set<String> wordsToReplace, String replacement) {
        this.wordsToReplace = wordsToReplace;
        this.replacement = replacement;
    }

    public Set<String> getWordsToReplace() {
        return wordsToReplace;
    }

    public void setWordsToReplace(Set<String> wordsToReplace) {
        this.wordsToReplace = wordsToReplace;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}

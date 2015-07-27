package com.merger;

import java.util.Set;

/**
 * @author lozov
 */
public class Replace {
    private Set<String> wordsToReplace;
    private String replacement;

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

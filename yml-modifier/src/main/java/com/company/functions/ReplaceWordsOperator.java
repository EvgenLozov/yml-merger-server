package com.company.functions;

import java.util.Arrays;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ReplaceWordsOperator implements UnaryOperator<String> {

    Set<String> words;

    public ReplaceWordsOperator(Set<String> words) {
        this.words = words;
    }

    @Override
    public String apply(String fragment) {
        return Arrays.asList(fragment.split(" ")).stream().filter( (word) -> !words.contains(word)).collect(Collectors.joining(" "));
    }
}

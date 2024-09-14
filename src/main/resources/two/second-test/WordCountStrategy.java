package org.omstu.common.strategies;

import org.omstu.interfaces.IStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCountStrategy implements IStrategy {
    private static final Pattern PATTERN = Pattern.compile("\\b\\w+\\b");

    public Object execute(Object... args) {
        Map<String, Integer> countInfo = new HashMap<>();

        String text = (String) args[0];
        text = text.toLowerCase();

        Matcher matcher = PATTERN.matcher(text);

        while (matcher.find()) {
            String word = matcher.group();
            countInfo.put(word, countInfo.getOrDefault(word, 0) + 1);
        }

        return countInfo;
    }
}

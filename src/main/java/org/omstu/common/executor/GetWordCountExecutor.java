package org.omstu.common.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetWordCountExecutor implements IExecutor {
    private static final Pattern PATTERN = Pattern.compile("\\b\\w+\\b");
    private String text;

    public GetWordCountExecutor(Object... args) {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException("Not enough arguments (needs 'file dsata')");
        }

        this.text = (String) args[0];
        this.text = text.toLowerCase();
    }

    public Object execute() {
        Map<String, Integer> countInfo = new HashMap<>();

        Matcher matcher = PATTERN.matcher(text);

        while (matcher.find()) {
            String word = matcher.group();
            countInfo.put(word, countInfo.getOrDefault(word, 0) + 1);
        }

        return countInfo;
    }
}

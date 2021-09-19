package com.weedro.whereismytime.daemon.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class StringUtils {
    private StringUtils() {
    }

    public static Optional<String[]> splitDelimiters(String string, int limit, String... delimiters) {
        for (String delimiter : delimiters)
            if (string.contains(delimiter))
                return Optional.of(string.split(delimiter, limit));

        return Optional.empty();
    }

    public static Map<String, String> mapDelimiters(String string, String... delimiters) {
        Map<String, String> result = new HashMap<String, String>();

        string.lines().forEach(line -> splitDelimiters(line, 2, delimiters)
                .filter(kv -> kv.length == 2)
                .ifPresent(kv -> result.put(kv[0], kv[1]))
        );

        return result;
    }
}

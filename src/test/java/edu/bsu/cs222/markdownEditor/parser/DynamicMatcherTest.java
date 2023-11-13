package edu.bsu.cs222.markdownEditor.parser;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamicMatcherTest {
    private final StringBuilder stringBuilder = new StringBuilder("*italic and **bold** markdown*");
    private final Pattern pattern = Pattern.compile("([*]{2}).+\\1");

    @Test
    public void replaceMatchInStringBuilder() {
        DynamicMatcher dynamicMatcher = new DynamicMatcher(pattern, stringBuilder);
        dynamicMatcher.forEachMatch(matcher -> {
        }); // Temporary use to call `matcher.find()`
        assertEquals("*italic and ~~~~~~~~ markdown*", stringBuilder.toString());
    }
}
package edu.bsu.cs222.markdownEditor.parser;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DynamicMatcher {

    private final Pattern pattern;
    private Matcher matcher;
    private final StringBuilder stringBuilder;

    DynamicMatcher(Pattern pattern, StringBuilder stringBuilder) {
        this.pattern = pattern;
        this.stringBuilder = stringBuilder;
    }

    void forEachMatch(Consumer<Matcher> action) {
        updateSearchedText();
        while (matcher.find()) {
            action.accept(matcher);
            replaceMatchText(matcher);
            updateSearchedText();
        }
    }

    private void updateSearchedText() {
        matcher = pattern.matcher(stringBuilder.toString());
    }

    private void replaceMatchText(Matcher matcher) {
        stringBuilder.replace(matcher.start(), matcher.end(), "~".repeat(matcher.group().length()));
    }
}

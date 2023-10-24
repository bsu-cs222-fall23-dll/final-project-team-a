package edu.bsu.cs222.markdownEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public enum InlineMarkdownType {
    italics("*", "i"),
    bold("**", "b");

    private final Pattern regexPattern;
    public final String className;

    InlineMarkdownType(String markdown, String className) {
        String escMarkdown = Pattern.quote(markdown);
        regexPattern = Pattern.compile(escMarkdown + ".+" + escMarkdown);
        this.className = className;
    }

    public List<InlineMarkdown> getOccurrences(String text) {
        List<InlineMarkdown> ranges = new ArrayList<>();
        Matcher matcher = regexPattern.matcher(text);
        while (matcher.find()) {
            ranges.add(new InlineMarkdown(matcher.start(), matcher.end()));
        }
        return ranges;
    }

}


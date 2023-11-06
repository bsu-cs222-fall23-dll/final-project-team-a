package edu.bsu.cs222.markdownEditor.textarea;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InlineMarkdown {
    Italics(TextStyle.Property.Italics,
            "(?<!\\\\)(?<openTag>[*])[^*]+?(?<!\\\\)(?<closeTag>\\1)(?!\\1)",
            "(?<!\\\\)(?<openTag>_)[^_]+?(?<!\\\\)(?<closeTag>\\1)(?!\\1)"),
    Bold(TextStyle.Property.Bold,
            "(?<!\\\\)(?<openTag>[*]{2})\\*?[^*]+?\\*?(?<!\\\\)(?<closeTag>\\1)(?!\\1)",
            "(?<!\\\\)(?<openTag>__)[^_]+?(?<!\\\\)(?<closeTag>\\1)(?!\\1)");

    public final TextStyle.Property styleProperty;
    final List<Pattern> patterns;

    InlineMarkdown(TextStyle.Property styleProperty, String... regexps) {
        this.styleProperty = styleProperty;
        patterns = Arrays.stream(regexps).map(regexp -> Pattern.compile(regexp, Pattern.MULTILINE)).toList();
    }

    public void forEachReference(String text, Consumer<Matcher> method) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) method.accept(matcher);
        }
    }

}

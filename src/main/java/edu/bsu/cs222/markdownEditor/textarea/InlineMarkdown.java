package edu.bsu.cs222.markdownEditor.textarea;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InlineMarkdown {
    Italics(List.of(TextStyle.Property.Italics),
            "(?<![*\\\\])([*])(?![*\\s]).+?(?<![*\\s\\\\])(\\1)",
            "(?<![_\\\\])(_)(?![_\\s]).+?(?<![_\\s\\\\])(\\1)"),
    Bold(List.of(TextStyle.Property.Bold),
            "(?<![*\\\\])([*]{2})(?![*\\s]).+?(?<![*\\s\\\\])(\\1)",
            "(?<![_\\\\])(__)(?![_\\s]).+?(?<![_\\s\\\\])(\\1)"),
    ItalicAndBold(List.of(TextStyle.Property.Italics, TextStyle.Property.Bold),
            "(?<![*\\\\])([*]{3})(?![*\\s]).+?(?<![*\\s\\\\])(\\1)",
            "(?<![_\\\\])(___)(?![_\\s]).+?(?<![_\\s\\\\])(\\1)"),
    Code(List.of(TextStyle.Property.Code),
            "(?<![`\\\\])(`)(?![`\\s]).+?(?<![`\\s\\\\])(\\1)");

    public final Collection<TextStyle.Property> textProperties;
    final List<Pattern> patterns;

    InlineMarkdown(Collection<TextStyle.Property> textProperties, String... regexps) {
        this.textProperties = textProperties;
        patterns = Arrays.stream(regexps).map(regexp -> Pattern.compile(regexp, Pattern.MULTILINE)).toList();
    }

    public void forEachReference(String text, Consumer<Matcher> method) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) method.accept(matcher);
        }
    }

}

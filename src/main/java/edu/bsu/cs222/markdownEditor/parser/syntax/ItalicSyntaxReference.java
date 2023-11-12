package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.parser.DynamicMatcher;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItalicSyntaxReference extends TagWrappedSyntaxReference {

    static private final TextStyle textStyle = TextStyle.EMPTY.add(TextStyle.Property.Italics);
    static private final List<String> regexps = List.of(
            "(?<![*\\\\])([*])(?![*\\s])(.+?)(?<![*\\s\\\\])(\\1)",
            "(?<![_\\\\])(_)(?![_\\s])(.+?)(?<![_\\s\\\\])(\\1)"
    );

    ItalicSyntaxReference(Matcher matcher) {
        super(matcher);
    }

    static public void forEachReference(StringBuilder stringBuilder, Consumer<ItalicSyntaxReference> action) {
        regexps.forEach(regexp -> {
            Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE);
            DynamicMatcher matcher = new DynamicMatcher(pattern, stringBuilder);
            matcher.forEachMatch(match -> action.accept(new ItalicSyntaxReference(match)));
        });
    }

    @Override
    public TextStyle getTextStyle() {
        return textStyle;
    }
}

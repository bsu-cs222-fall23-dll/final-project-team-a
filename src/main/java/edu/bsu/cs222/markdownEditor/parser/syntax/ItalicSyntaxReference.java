package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;

import java.util.ArrayList;
import java.util.List;
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

    static public List<ItalicSyntaxReference> findReferences(String text) {
        List<ItalicSyntaxReference> references = new ArrayList<>();
        regexps.forEach(regexp -> {
            Matcher matcher = Pattern.compile(regexp, Pattern.MULTILINE).matcher(text);
            while (matcher.find()) references.add(new ItalicSyntaxReference(matcher));
        });
        return references;
    }

    @Override
    public TextStyle getTextStyle() {
        return textStyle;
    }
}

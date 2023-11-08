package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItalicAndBoldSyntaxReference extends TagWrappedSyntaxReference {

    static private final TextStyle textStyle = TextStyle.EMPTY.add(TextStyle.Property.Italics).add(TextStyle.Property.Bold);
    static private final List<String> regexps = List.of(
            "(?<![*\\\\])([*]{3})(?![*\\s])(.+?)(?<![*\\s\\\\])(\\1)",
            "(?<![_\\\\])(___)(?![_\\s])(.+?)(?<![_\\s\\\\])(\\1)"
    );

    ItalicAndBoldSyntaxReference(Matcher matcher) {
        super(matcher);
    }

    static public List<ItalicAndBoldSyntaxReference> findReferences(String text) {
        List<ItalicAndBoldSyntaxReference> references = new ArrayList<>();
        regexps.forEach(regexp -> {
            Matcher matcher = Pattern.compile(regexp, Pattern.MULTILINE).matcher(text);
            while (matcher.find()) references.add(new ItalicAndBoldSyntaxReference(matcher));
        });
        return references;
    }

    @Override
    public TextStyle getTextStyle() {
        return textStyle;
    }
}

package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoldSyntaxReference extends TagWrappedSyntaxReference {

    static private final TextStyle textStyle = TextStyle.EMPTY.add(TextStyle.Property.Bold);
    static private final List<String> regexps = List.of(
            "(?<![*\\\\])([*]{2})(?![*\\s])(.+?)(?<![*\\s\\\\])(\\1)",
            "(?<![_\\\\])(__)(?![_\\s])(.+?)(?<![_\\s\\\\])(\\1)"
    );

    BoldSyntaxReference(Matcher matcher) {
        super(matcher);
    }

    static public List<BoldSyntaxReference> findReferences(String text) {
        List<BoldSyntaxReference> references = new ArrayList<>();
        regexps.forEach(regexp -> {
            Matcher matcher = Pattern.compile(regexp, Pattern.MULTILINE).matcher(text);
            while (matcher.find()) references.add(new BoldSyntaxReference(matcher));
        });
        return references;
    }

    @Override
    public TextStyle getTextStyle() {
        return textStyle;
    }
}

package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeSyntaxReference extends TagWrappedSyntaxReference {

    static private final TextStyle textStyle = TextStyle.EMPTY.add(TextStyle.Property.Code);
    static private final String regexp = "(?<![`\\\\])(`)(?![`\\s])(.+?)(?<![`\\s\\\\])(\\1)";

    CodeSyntaxReference(Matcher matcher) {
        super(matcher);
    }

    static public List<CodeSyntaxReference> findReferences(String text) {
        List<CodeSyntaxReference> references = new ArrayList<>();
        Matcher matcher = Pattern.compile(regexp, Pattern.MULTILINE).matcher(text);
        while (matcher.find()) references.add(new CodeSyntaxReference(matcher));
        return references;
    }

    @Override
    public TextStyle getTextStyle() {
        return textStyle;
    }
}

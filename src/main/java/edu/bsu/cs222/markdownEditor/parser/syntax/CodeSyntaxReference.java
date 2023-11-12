package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.parser.DynamicMatcher;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeSyntaxReference extends TagWrappedSyntaxReference {

    static private final TextStyle textStyle = TextStyle.EMPTY.add(TextStyle.Property.Code);
    static private final String regexp = "(?<![`\\\\])(`)(?![`\\s])(.+?)(?<![`\\s\\\\])(\\1)";

    CodeSyntaxReference(Matcher matcher) {
        super(matcher);
    }

    static public void forEachReference(StringBuilder stringBuilder, Consumer<CodeSyntaxReference> action) {
        Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE);
        DynamicMatcher matcher = new DynamicMatcher(pattern, stringBuilder);
        matcher.forEachMatch(match -> action.accept(new CodeSyntaxReference(match)));
    }

    @Override
    public TextStyle getTextStyle() {
        return textStyle;
    }
}

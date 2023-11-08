package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.scene.control.IndexRange;

import java.util.regex.Matcher;

abstract public class TagWrappedSyntaxReference extends SyntaxReference {
    private final String tag, content;

    TagWrappedSyntaxReference(Matcher matcher) {
        super(new IndexRange(matcher.start(), matcher.end()));
        tag = matcher.group(1);
        content = matcher.group(2);
    }

    public abstract TextStyle getTextStyle();

    public int getTagLength() {
        return tag.length();
    }

    public int getContentLength() {
        return content.length();
    }
}

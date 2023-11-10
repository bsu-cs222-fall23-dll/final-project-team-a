package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.scene.control.IndexRange;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.regex.Matcher;

abstract public class TagWrappedSyntaxReference extends SyntaxReference {
    private final String tag, content;

    TagWrappedSyntaxReference(Matcher matcher) {
        super(new IndexRange(matcher.start(), matcher.end()));
        tag = matcher.group(1);
        content = matcher.group(2);
    }

    public abstract TextStyle getTextStyle();

    public StyleSpans<TextStyle> getStyleSpans() {
        StyleSpansBuilder<TextStyle> styleSpansBuilder = new StyleSpansBuilder<>();
        styleSpansBuilder.add(getTextStyle().add(TextStyle.Property.Markdown), tag.length());
        styleSpansBuilder.add(getTextStyle(), content.length());
        styleSpansBuilder.add(getTextStyle().add(TextStyle.Property.Markdown), tag.length());
        return styleSpansBuilder.create();
    }
}

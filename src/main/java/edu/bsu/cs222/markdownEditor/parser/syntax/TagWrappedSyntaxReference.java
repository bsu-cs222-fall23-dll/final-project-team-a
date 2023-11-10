package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenSyntaxSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.List;
import java.util.regex.Matcher;

abstract public class TagWrappedSyntaxReference extends SyntaxReference {
    private final String tag, text;

    TagWrappedSyntaxReference(Matcher matcher) {
        super(matcher);
        tag = matcher.group(1);
        text = matcher.group(2);
    }

    public abstract TextStyle getTextStyle();

    public List<Segment> getMarkdownSegments() {
        return List.of(new TextSegment(tag + text + tag));
    }

    public List<Segment> getRenderedSegments() {
        return List.of(new HiddenSyntaxSegment(tag),
                new TextSegment(text),
                new HiddenSyntaxSegment(tag));
    }

    public StyleSpans<TextStyle> getStyleSpans() {
        StyleSpansBuilder<TextStyle> styleSpansBuilder = new StyleSpansBuilder<>();
        styleSpansBuilder.add(getTextStyle().add(TextStyle.Property.Markdown), tag.length());
        styleSpansBuilder.add(getTextStyle(), text.length());
        styleSpansBuilder.add(getTextStyle().add(TextStyle.Property.Markdown), tag.length());
        return styleSpansBuilder.create();
    }
}

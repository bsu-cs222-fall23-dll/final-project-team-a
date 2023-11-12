package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenSyntaxSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.regex.Matcher;

public class ParagraphSyntaxReference extends SyntaxReference {
    protected final String syntax;
    public final ParagraphStyle paragraphStyle;

    ParagraphSyntaxReference(Matcher matcher, ParagraphStyle paragraphStyle) {
        super(matcher);
        syntax = matcher.group();
        this.paragraphStyle = paragraphStyle;
    }

    @Override
    public SegmentList getMarkdownSegments() {
        return new SegmentList(new TextSegment(syntax));
    }

    @Override
    public SegmentList getRenderedSegments() {
        return new SegmentList(new HiddenSyntaxSegment(syntax));
    }

    @Override
    public StyleSpans<TextStyle> getStyleSpans() {
        StyleSpansBuilder<TextStyle> styleSpansBuilder = new StyleSpansBuilder<>();
        styleSpansBuilder.add(TextStyle.MARKDOWN, syntax.length());
        return styleSpansBuilder.create();
    }

    @Override
    String getText() {
        return null;
    }

    @Override
    int getTextStart() {
        return 0;
    }

    public ParagraphStyle getParagraphStyle() {
        return paragraphStyle;
    }
}

package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;

import java.util.regex.Matcher;

public class OrderedListSyntaxReference extends ParagraphSyntaxReference {
    private final String text;

    OrderedListSyntaxReference(Matcher matcher) {
        super(matcher, ParagraphStyle.OrderedList);
        text = matcher.group(1);
    }

    @Override
    public ParagraphStyle getParagraphStyle() {
        return ParagraphStyle.OrderedList;
    }

    @Override
    public SegmentList getMarkdownSegments() {
        return new SegmentList(new TextSegment(text));
    }

    @Override
    public SegmentList getRenderedSegments() {
        return new SegmentList(new TextSegment(text));
    }
}

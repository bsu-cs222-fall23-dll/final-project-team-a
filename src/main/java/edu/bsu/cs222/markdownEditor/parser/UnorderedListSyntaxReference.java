package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.BulletPointSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;

import java.util.regex.Matcher;

class UnorderedListSyntaxReference extends ParagraphSyntaxReference {

    UnorderedListSyntaxReference(Matcher matcher) {
        super(matcher, ParagraphStyle.UnorderedList);
    }

    @Override
    public SegmentList getMarkdownSegments() {
        return new SegmentList(BulletPointSegment.MARKDOWN);
    }

    @Override
    public SegmentList getRenderedSegments() {
        return new SegmentList(BulletPointSegment.RENDERED);
    }
}

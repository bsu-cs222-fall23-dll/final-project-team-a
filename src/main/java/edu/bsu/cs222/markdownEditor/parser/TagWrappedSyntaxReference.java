package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenSyntaxSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.regex.Matcher;

class TagWrappedSyntaxReference extends SyntaxReference {
    final InlineSyntaxType type;
    final String tag;
    private final String text;

    TagWrappedSyntaxReference(InlineSyntaxType type, Matcher matcher) {
        super(matcher);
        this.type = type;
        tag = matcher.group(1);
        text = matcher.group(2);
    }

    public SegmentList getMarkdownSegments() {
        SegmentList map = new SegmentList(start);
        map.add(new TextSegment(tag));
        map.skip(text.length());
        map.add(new TextSegment(tag));
        return map;
    }

    public SegmentList getRenderedSegments() {
        SegmentList map = new SegmentList(start);
        map.add(new HiddenSyntaxSegment(tag));
        map.skip(text.length());
        map.add(new HiddenSyntaxSegment(tag));
        return map;
    }

    public StyleSpans<TextStyle> getMarkdownStyleSpans() {
        StyleSpansBuilder<TextStyle> styleSpansBuilder = new StyleSpansBuilder<>();
        styleSpansBuilder.add(type.getTextStyle().add(TextStyle.Property.Markdown), tag.length());
        styleSpansBuilder.add(type.getTextStyle(), text.length());
        styleSpansBuilder.add(type.getTextStyle().add(TextStyle.Property.Markdown), tag.length());
        return styleSpansBuilder.create();
    }

    @Override
    String getText() {
        return text;
    }

    @Override
    int getTextStart() {
        return start + tag.length();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TagWrappedSyntaxReference syntaxReference)
            return this.type.equals(syntaxReference.type) &&
                    this.text.equals(syntaxReference.text) &&
                    this.tag.equals(syntaxReference.tag);
        else return super.equals(obj);
    }
}

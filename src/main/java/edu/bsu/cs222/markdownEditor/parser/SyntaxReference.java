package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.regex.Matcher;

public abstract class SyntaxReference {
    public int start;
    public final String fullText;

    SyntaxReference(Matcher matcher) {
        start = matcher.start();
        fullText = matcher.group();
    }

    public abstract SegmentList getMarkdownSegments();

    public abstract SegmentList getRenderedSegments();

    public abstract StyleSpans<TextStyle> getStyleSpans();

    abstract String getText();

    abstract int getTextStart();

    void offsetStart(int length) {
        start += length;
    }

    boolean hasStylableText() {
        return getText() != null;
    }

}

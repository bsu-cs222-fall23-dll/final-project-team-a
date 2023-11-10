package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.List;
import java.util.regex.Matcher;

public abstract class SyntaxReference {
    public final int start;
    public final String fullText;

    SyntaxReference(Matcher matcher) {
        start = matcher.start();
        fullText = matcher.group();
    }

    public abstract TextStyle getTextStyle();

    public abstract List<Segment> getMarkdownSegments();

    public abstract List<Segment> getRenderedSegments();

    public abstract StyleSpans<TextStyle> getStyleSpans();
}

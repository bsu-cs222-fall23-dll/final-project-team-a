package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import javafx.scene.control.IndexRange;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.List;

public abstract class SyntaxReference {
    public final IndexRange range;

    SyntaxReference(IndexRange range) {
        this.range = range;
    }

    public abstract TextStyle getTextStyle();

    public abstract List<Segment> getMarkdownSegments();

    public abstract List<Segment> getRenderedSegments();

    public abstract StyleSpans<TextStyle> getStyleSpans();
}

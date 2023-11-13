package edu.bsu.cs222.markdownEditor.textarea.segments;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.geometry.VPos;
import org.fxmisc.richtext.TextExt;

public class TextSegment extends Segment {

    public TextSegment(String text) {
        super(text);
    }

    public TextSegment subSequence(int start, int end) {
        return new TextSegment(text.substring(start, end));
    }

    @Override
    Segment create(String text) {
        return new TextSegment(text);
    }

    @Override
    public TextExt configureNode(TextStyle style) {
        TextExt textNode = new TextExt();
        textNode.setTextOrigin(VPos.TOP);
        textNode.getStyleClass().addAll(style.toList());
        textNode.setText(text);
        return textNode;
    }
}

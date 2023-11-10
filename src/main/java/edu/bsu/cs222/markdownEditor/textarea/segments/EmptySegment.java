package edu.bsu.cs222.markdownEditor.textarea.segments;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.scene.Node;
import org.fxmisc.richtext.TextExt;

public class EmptySegment extends Segment {
    public EmptySegment() {
        super("");
    }

    @Override
    public EmptySegment subSequence(int start, int end) {
        throw new RuntimeException("EmptySegment should never be used");
    }

    @Override
    public Node configureNode(TextStyle style) {
        return new TextExt("");
    }

    @Override
    protected EmptySegment create(String text) {
        throw new RuntimeException("EmptySegment should never be used");
    }
}

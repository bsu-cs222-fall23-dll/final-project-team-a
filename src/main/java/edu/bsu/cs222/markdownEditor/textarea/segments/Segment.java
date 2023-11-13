package edu.bsu.cs222.markdownEditor.textarea.segments;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.scene.Node;

public abstract class Segment {
    protected final String text;

    public Segment(String text) {
        this.text = text;
    }

    public abstract Segment subSequence(int start, int end);

    abstract Segment create(String text);

    public abstract Node configureNode(TextStyle style);

    public int length() {
        return text.length();
    }

    public char charAt(int index) {
        return text.charAt(index);
    }

    public String getText() {
        return text;
    }
}

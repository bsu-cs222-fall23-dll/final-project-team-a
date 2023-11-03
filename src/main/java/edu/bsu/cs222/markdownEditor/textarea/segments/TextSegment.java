package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.fxmisc.richtext.TextExt;

public class TextSegment {

    private final String text;

    public TextSegment() {
        this("");
    }

    public TextSegment(String text) {
        this.text = text;
    }

    public int length() {
        return text.length();
    }

    public char charAt(int index) {
        return text.charAt(index);
    }

    public String getText() {
        return text;
    }

    public TextSegment subSequence(int start, int end) {
        return new TextSegment(text.substring(start, end));
    }

    public TextExt configureNode(TextExt textNode) {
        textNode.setText(text);
        return textNode;
    }
}

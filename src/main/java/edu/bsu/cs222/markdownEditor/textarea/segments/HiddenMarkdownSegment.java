package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.fxmisc.richtext.TextExt;

public class HiddenMarkdownSegment {

    private final String text;

    public HiddenMarkdownSegment() {
        this("");
    }

    public HiddenMarkdownSegment(String text) {
        this.text = text;
    }

    public boolean isPlainText() {
        return true;
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

    public HiddenMarkdownSegment subSequence(int start, int end) {
        return new HiddenMarkdownSegment(text.substring(start, end));
    }

    public TextExt configureNode(TextExt textNode) {
        textNode.setText(text);
        textNode.setVisible(false);
        textNode.setManaged(false);
        return textNode;
    }
}

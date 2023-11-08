package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.fxmisc.richtext.TextExt;

public class RenderedMarkdownSegment {

    private final String text;

    public RenderedMarkdownSegment() {
        this("");
    }

    public RenderedMarkdownSegment(String text) {
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

    public RenderedMarkdownSegment subSequence(int start, int end) {
        return new RenderedMarkdownSegment(text.substring(start, end));
    }

    public TextExt configureNode(TextExt textNode) {
        textNode.setText(text);
        textNode.setVisible(false);
        textNode.setManaged(false);
        return textNode;
    }
}

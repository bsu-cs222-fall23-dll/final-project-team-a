package edu.bsu.cs222.markdownEditor.textarea.segments;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import org.fxmisc.richtext.TextExt;

public class HyperlinkSegment extends TextSegment {
    private final String urlString;

    public HyperlinkSegment(String urlString) {
        this(urlString, urlString);
    }

    public HyperlinkSegment(String text, String urlString) {
        super(text);
        this.urlString = urlString;
    }

    @Override
    public TextExt configureNode(TextStyle style) {
        TextExt textNode = super.configureNode(style);
        textNode.setOnMouseClicked(event -> {
            // TODO: open link
            System.out.println(urlString);
        });
        textNode.setOnMouseEntered(event -> {
            // TODO: override default click behavior
        });
        textNode.setOnMouseExited(event -> {
            // TODO: reset default click behavior
        });
        return textNode;
    }

    public HyperlinkSegment subSequence(int start, int end) {
        return new HyperlinkSegment(text.substring(start, end));
    }

}

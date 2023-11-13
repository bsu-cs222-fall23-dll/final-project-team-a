package edu.bsu.cs222.markdownEditor.textarea.segments;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import org.fxmisc.richtext.TextExt;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
            if (event.isShortcutDown()) {
                try {
                    Desktop.getDesktop().browse(new URI(urlString));
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return textNode;
    }

    public HyperlinkSegment subSequence(int start, int end) {
        return new HyperlinkSegment(text.substring(start, end));
    }

}

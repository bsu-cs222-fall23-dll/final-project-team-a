package edu.bsu.cs222.markdownEditor.segments;

import javafx.geometry.VPos;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.StyledSegment;

import java.util.Collection;

public class Markdown {

    private final String text;

    public Markdown() {
        this("");
    }

    public Markdown(String text) {
        this.text = text;
    }

    public boolean isPlainText() {
        return true;
    }

    public static TextExt nodeFactory(StyledSegment<Markdown, Collection<String>> styledSegment) {
        Markdown markdown = styledSegment.getSegment();
        TextExt textNode = new TextExt();
        textNode.setTextOrigin(VPos.TOP);
        textNode.getStyleClass().addAll(styledSegment.getStyle());
        textNode.setText(markdown.text);
        return textNode;
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

    public Markdown subSequence(int start, int end) {
        return new Markdown(text.substring(start, end));
    }
}

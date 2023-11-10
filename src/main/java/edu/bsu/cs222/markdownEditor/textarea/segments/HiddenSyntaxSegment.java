package edu.bsu.cs222.markdownEditor.textarea.segments;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import org.fxmisc.richtext.TextExt;

public class HiddenSyntaxSegment extends TextSegment {

    public HiddenSyntaxSegment(String text) {
        super(text);
    }

    @Override
    public HiddenSyntaxSegment subSequence(int start, int end) {
        return new HiddenSyntaxSegment(text.substring(start, end));
    }

    public TextExt configureNode(TextStyle style) {
        TextExt textNode = super.configureNode(style);
        textNode.setVisible(false);
        textNode.setManaged(false);
        return textNode;
    }
}

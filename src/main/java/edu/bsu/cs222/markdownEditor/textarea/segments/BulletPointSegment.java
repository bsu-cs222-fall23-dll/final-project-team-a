package edu.bsu.cs222.markdownEditor.textarea.segments;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import org.fxmisc.richtext.TextExt;

public class BulletPointSegment extends TextSegment {
    public static final BulletPointSegment MARKDOWN = new BulletPointSegment(false);
    public static final BulletPointSegment RENDERED = new BulletPointSegment(true);

    private final boolean isRendered;

    private BulletPointSegment(boolean isRendered) {
        this("- ", isRendered);
    }

    private BulletPointSegment(String text, boolean isRendered) {
        super(text);
        this.isRendered = isRendered;
    }

    @Override
    public BulletPointSegment subSequence(int start, int end) {
        return new BulletPointSegment(text.substring(start, end), isRendered);
    }

    @Override
    BulletPointSegment create(String text) {
        return new BulletPointSegment(text, isRendered);
    }

    @Override
    public TextExt configureNode(TextStyle style) {
        TextExt textNode = super.configureNode(style);
        if (isRendered) textNode.setText("• ");
        return textNode;
    }
}

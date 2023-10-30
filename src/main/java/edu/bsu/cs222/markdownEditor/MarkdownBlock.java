package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.segments.Markdown;
import edu.bsu.cs222.markdownEditor.segments.MarkdownOps;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.TextOps;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MarkdownBlock extends GenericStyledArea<Void, Markdown, Collection<String>> {

    private static final TextOps<Markdown, Collection<String>> MARKDOWN_OPS = new MarkdownOps<>();

    public MarkdownBlock() {
        super(null, MarkdownBlock::applyParagraphStyle, Collections.emptyList(), MARKDOWN_OPS, Markdown::nodeFactory);
        getStyleClass().add("markdown-editor");
        new EventManager(this);
    }

    private static void applyParagraphStyle(TextFlow textFlow, Void paragraphStyle) {
    }

    public void setStyleClass(int start, int end, String... classNames) {
        setStyle(start, end, List.of(classNames));
    }

    public List<Markdown> getSegments(int start, int end) {
        return this.getDocument().subSequence(start, end).getParagraph(0).getSegments();
    }

    public void replace(int start, int end, Markdown markdown) {
        replace(start, end, ReadOnlyStyledDocument.fromSegment(markdown, null, Collections.emptyList(), MARKDOWN_OPS));
    }
}

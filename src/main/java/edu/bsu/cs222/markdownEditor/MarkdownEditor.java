package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.segments.Markdown;
import edu.bsu.cs222.markdownEditor.segments.MarkdownOps;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.TextOps;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MarkdownEditor extends GenericStyledArea<MarkdownBlockType, Markdown, Collection<String>> {

    private static final TextOps<Markdown, Collection<String>> MARKDOWN_OPS = new MarkdownOps<>();

    public MarkdownEditor() {
        super(MarkdownBlockType.Paragraph, MarkdownEditor::applyParagraphStyle, Collections.emptyList(), MARKDOWN_OPS, Markdown::nodeFactory);
        getStyleClass().add("markdown-editor");
        new EventManager(this);
    }

    private static void applyParagraphStyle(TextFlow textFlow, MarkdownBlockType paragraphStyle) {
        textFlow.getStyleClass().add(paragraphStyle.className);
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

    public void checkParagraphType() {
        int paragraphIndex = getCurrentParagraph();
        Paragraph<MarkdownBlockType, Markdown, Collection<String>> paragraph = getParagraph(paragraphIndex);
        MarkdownBlockType blockType = paragraph.getParagraphStyle();
        if (blockType == MarkdownBlockType.Paragraph || !blockType.matches(paragraph.getText())) {
            MarkdownBlockType newStyle = MarkdownBlockType.findType(paragraph.getText());
            setParagraphStyle(paragraphIndex, newStyle);
        }
    }
}

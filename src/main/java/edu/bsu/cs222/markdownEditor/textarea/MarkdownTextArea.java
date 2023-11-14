package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.StyledSegment;

public class MarkdownTextArea
        extends GenericStyledArea<ParagraphStyle, Segment, TextStyle>
        implements ParagraphStyleActions, TextStyleActions, MarkdownSyntaxActions {

    public MarkdownTextArea() {
        super(ParagraphStyle.Paragraph,
                MarkdownTextArea::applyParagraphStyle,
                TextStyle.EMPTY,
                SEGMENT_OPS,
                MarkdownTextArea::nodeFactory);
        getStyleClass().add("markdown-editor");
        new EventManager(this).initialize();
    }

    private static void applyParagraphStyle(TextFlow textFlow, ParagraphStyle paragraphStyle) {
        textFlow.getStyleClass().add(paragraphStyle.className);
    }

    private static Node nodeFactory(StyledSegment<Segment, TextStyle> styledSegment) {
        Segment segment = styledSegment.getSegment();
        TextStyle style = styledSegment.getStyle();
        return segment.configureNode(style);
    }

    public void setText(String content) {
        deleteText(0, getLength());
        insertText(0, content);
        showParagraphMarkdown(0);
        for (int i = 1; i < getParagraphs().size(); i++) {
            hideParagraphMarkdown(i);
        }
    }
}

package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.MarkdownSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.MarkdownSegmentOps;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegmentOps;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

import java.util.Optional;

public class MarkdownTextArea extends GenericStyledArea<MarkdownBlockType, Either<TextSegment, MarkdownSegment>, TextStyle> {

    private static final TextOps<TextSegment, TextStyle> TEXT_SEGMENT_OPS = new TextSegmentOps<>();
    private static final TextOps<MarkdownSegment, TextStyle> MARKDOWN_SEGMENT_OPS = new MarkdownSegmentOps<>();
    private static final TextOps<Either<TextSegment, MarkdownSegment>, TextStyle> EITHER_OPS = TEXT_SEGMENT_OPS._or(MARKDOWN_SEGMENT_OPS, (s1, s2) -> Optional.empty());

    public MarkdownTextArea() {
        super(MarkdownBlockType.Paragraph, MarkdownTextArea::applyParagraphStyle, TextStyle.EMPTY, EITHER_OPS, MarkdownTextArea::nodeFactory);
        getStyleClass().add("markdown-editor");
        new EventManager(this).initialize();
    }

    private static void applyParagraphStyle(TextFlow textFlow, MarkdownBlockType paragraphStyle) {
        textFlow.getStyleClass().add(paragraphStyle.className);
    }

    private static Node nodeFactory(StyledSegment<Either<TextSegment, MarkdownSegment>, TextStyle> styledSegment) {
        TextExt textNode = new TextExt();
        textNode.setTextOrigin(VPos.TOP);
        textNode.getStyleClass().addAll(styledSegment.getStyle().toList());
        styledSegment.getSegment().unify(
                textSegment -> textSegment.configureNode(textNode),
                markdownSegment -> markdownSegment.configureNode(textNode)
        );
        return textNode;
    }

    public void setCurrentParagraphType(MarkdownBlockType style) {
        setParagraphStyle(getCurrentParagraph(), style);
    }

    public void checkCurrentParagraphType() {
        checkParagraphType(getCurrentParagraph());
    }

    private void checkParagraphType(int index) {
        MarkdownBlockType blockType = getParagraph(index).getParagraphStyle();
        String text = getParagraph(index).getText();
        if (blockType == MarkdownBlockType.Paragraph || !blockType.matches(text)) {
            MarkdownBlockType newStyle = MarkdownBlockType.findType(text);
            setParagraphStyle(index, newStyle);
        }
    }
}

package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.segments.MarkdownSegment;
import edu.bsu.cs222.markdownEditor.segments.MarkdownSegmentOps;
import edu.bsu.cs222.markdownEditor.segments.TextSegment;
import edu.bsu.cs222.markdownEditor.segments.TextSegmentOps;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class MarkdownTextArea extends GenericStyledArea<MarkdownBlockType, Either<TextSegment, MarkdownSegment>, Collection<String>> {

    private static final TextOps<TextSegment, Collection<String>> TEXT_SEGMENT_OPS = new TextSegmentOps<>();
    private static final TextOps<MarkdownSegment, Collection<String>> MARKDOWN_SEGMENT_OPS = new MarkdownSegmentOps<>();
    private static final TextOps<Either<TextSegment, MarkdownSegment>, Collection<String>> EITHER_OPS = TEXT_SEGMENT_OPS._or(MARKDOWN_SEGMENT_OPS, (s1, s2) -> Optional.empty());

    public MarkdownTextArea() {
        super(MarkdownBlockType.Paragraph, MarkdownTextArea::applyParagraphStyle, Collections.emptyList(), EITHER_OPS, MarkdownTextArea::nodeFactory);
        getStyleClass().add("markdown-editor");
    }

    private static void applyParagraphStyle(TextFlow textFlow, MarkdownBlockType paragraphStyle) {
        textFlow.getStyleClass().add(paragraphStyle.className);
    }

    private static Node nodeFactory(StyledSegment<Either<TextSegment, MarkdownSegment>, Collection<String>> styledSegment) {
        TextExt textNode = new TextExt();
        textNode.setTextOrigin(VPos.TOP);
        textNode.getStyleClass().addAll(styledSegment.getStyle());
        styledSegment.getSegment().unify(
                textSegment -> textSegment.styleNode(textNode),
                markdownSegment -> markdownSegment.styleNode(textNode)
        );
        return textNode;
    }

    public String getParagraphText(int index) {
        return getParagraph(index).getText();
    }

    public MarkdownBlockType getParagraphStyle(int index) {
        return getParagraph(index).getParagraphStyle();
    }
}

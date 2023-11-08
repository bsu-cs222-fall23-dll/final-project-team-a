package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.RenderedMarkdownSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.StyledSegment;
import org.reactfx.util.Either;

public class MarkdownTextArea
        extends GenericStyledArea<ParagraphStyle, Either<TextSegment, RenderedMarkdownSegment>, TextStyle>
        implements ParagraphStyleActions, TextStyleActions, MarkdownSyntaxActions {

    public MarkdownTextArea() {
        super(ParagraphStyle.Paragraph,
                MarkdownTextArea::applyParagraphStyle,
                TextStyle.EMPTY,
                EITHER_OPS,
                MarkdownTextArea::nodeFactory);
        getStyleClass().add("markdown-editor");
        new EventManager(this).initialize();
    }

    private static void applyParagraphStyle(TextFlow textFlow, ParagraphStyle paragraphStyle) {
        textFlow.getStyleClass().add(paragraphStyle.className);
    }

    private static Node nodeFactory(StyledSegment<Either<TextSegment, RenderedMarkdownSegment>, TextStyle> styledSegment) {
        TextExt textNode = new TextExt();
        textNode.setTextOrigin(VPos.TOP);
        textNode.getStyleClass().addAll(styledSegment.getStyle().toList());
        styledSegment.getSegment().unify(
                textSegment -> textSegment.configureNode(textNode),
                renderedMarkdownSegment -> renderedMarkdownSegment.configureNode(textNode)
        );
        return textNode;
    }
}

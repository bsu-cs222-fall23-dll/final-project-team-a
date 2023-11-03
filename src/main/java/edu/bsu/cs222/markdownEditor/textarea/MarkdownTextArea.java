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
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
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

    @Override
    public void setParagraphStyle(int paragraphIndex, MarkdownBlockType paragraphStyle) {
        MarkdownBlockType currentStyle = getParagraphType(paragraphIndex);
        if (hasSyntaxForParagraphStyle(paragraphIndex, currentStyle) && currentStyle != MarkdownBlockType.Paragraph)
            removeSyntaxForParagraphStyle(paragraphIndex, currentStyle);
        super.setParagraphStyle(paragraphIndex, paragraphStyle);
        if (!hasSyntaxForParagraphStyle(paragraphIndex, paragraphStyle) && paragraphStyle != MarkdownBlockType.Paragraph)
            addSyntaxForParagraphStyle(paragraphIndex, paragraphStyle);
    }

    public void setCurrentParagraphStyle(MarkdownBlockType style) {
        this.setParagraphStyle(getCurrentParagraph(), style);
    }

    public void checkCurrentParagraphType() {
        checkParagraphType(getCurrentParagraph());
    }

    private void checkParagraphType(int index) {
        MarkdownBlockType blockType = getParagraphType(index);
        String text = getParagraphText(index);
        if (blockType == MarkdownBlockType.Paragraph) {
            MarkdownBlockType newStyle = MarkdownBlockType.findType(text);
            if (newStyle != MarkdownBlockType.Paragraph) setParagraphStyle(index, newStyle);
        } else if (!blockType.matches(text)) {
            MarkdownBlockType newStyle = MarkdownBlockType.findType(text);
            setParagraphStyle(index, newStyle);
        }
    }

    private boolean hasSyntaxForParagraphStyle(int paragraphIndex, MarkdownBlockType style) {
        String text = getParagraphText(paragraphIndex);
        return style.matches(text);
    }

    private void removeSyntaxForParagraphStyle(int paragraphIndex, MarkdownBlockType style) {
        String text = getParagraphText(paragraphIndex);
        int end = style.getMarkdownSyntax(text).length();
        deleteText(paragraphIndex, 0, paragraphIndex, end);
    }

    private void addSyntaxForParagraphStyle(int paragraphIndex, MarkdownBlockType style) {
        MarkdownSegment segment = new MarkdownSegment(style.createMarkdownSyntax());
        replace(paragraphIndex, 0, paragraphIndex, 0, ReadOnlyStyledDocument.fromSegment(eitherWrap(segment), style, getInitialTextStyle(), EITHER_OPS));
    }

    private MarkdownBlockType getParagraphType(int index) {
        return getParagraph(index).getParagraphStyle();
    }

    private String getParagraphText(int index) {
        return getParagraph(index).getText();
    }

    private Either<TextSegment, MarkdownSegment> eitherWrap(MarkdownSegment segment) {
        return Either.right(segment);
    }
}

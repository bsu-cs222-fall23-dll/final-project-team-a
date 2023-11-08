package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenMarkdownSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenMarkdownSegmentOps;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegmentOps;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

import java.util.Optional;

public class MarkdownTextArea extends GenericStyledArea<ParagraphStyle, Either<TextSegment, HiddenMarkdownSegment>, TextStyle> {

    private static final TextOps<TextSegment, TextStyle> TEXT_SEGMENT_OPS = new TextSegmentOps<>();
    private static final TextOps<HiddenMarkdownSegment, TextStyle> HIDDEN_MARKDOWN_SEGMENT_OPS = new HiddenMarkdownSegmentOps<>();
    private static final TextOps<Either<TextSegment, HiddenMarkdownSegment>, TextStyle> EITHER_OPS = TEXT_SEGMENT_OPS._or(HIDDEN_MARKDOWN_SEGMENT_OPS, (s1, s2) -> Optional.empty());

    public MarkdownTextArea() {
        super(ParagraphStyle.Paragraph, MarkdownTextArea::applyParagraphStyle, TextStyle.EMPTY, EITHER_OPS, MarkdownTextArea::nodeFactory);
        getStyleClass().add("markdown-editor");
        new EventManager(this).initialize();
    }

    private static void applyParagraphStyle(TextFlow textFlow, ParagraphStyle paragraphStyle) {
        textFlow.getStyleClass().add(paragraphStyle.className);
    }

    private static Node nodeFactory(StyledSegment<Either<TextSegment, HiddenMarkdownSegment>, TextStyle> styledSegment) {
        TextExt textNode = new TextExt();
        textNode.setTextOrigin(VPos.TOP);
        textNode.getStyleClass().addAll(styledSegment.getStyle().toList());
        styledSegment.getSegment().unify(
                textSegment -> textSegment.configureNode(textNode),
                hiddenMarkdownSegment -> hiddenMarkdownSegment.configureNode(textNode)
        );
        return textNode;
    }

    @Override
    public void setParagraphStyle(int paragraphIndex, ParagraphStyle paragraphStyle) {
        ParagraphStyle currentStyle = getParagraphType(paragraphIndex);
        if (hasSyntaxForParagraphStyle(paragraphIndex, currentStyle) && currentStyle != ParagraphStyle.Paragraph)
            removeSyntaxForParagraphStyle(paragraphIndex, currentStyle);
        super.setParagraphStyle(paragraphIndex, paragraphStyle);
        if (!hasSyntaxForParagraphStyle(paragraphIndex, paragraphStyle) && paragraphStyle != ParagraphStyle.Paragraph)
            addSyntaxForParagraphStyle(paragraphIndex, paragraphStyle);
    }

    public void setCurrentParagraphStyle(ParagraphStyle style) {
        this.setParagraphStyle(getCurrentParagraph(), style);
    }

    public void checkCurrentParagraphStyle() {
        checkParagraphStyle(getCurrentParagraph());
    }

    public void addStyle(int paragraph, int from, int to, TextStyle style) {
        StyleSpans<TextStyle> newSpans = getStyleSpans(paragraph, from, to).mapStyles(span -> span.concat(style));
        setStyleSpans(paragraph, from, newSpans);
    }


    private void checkParagraphStyle(int index) {
        ParagraphStyle style = getParagraphType(index);
        String text = getParagraphText(index);
        if (style == ParagraphStyle.Paragraph) {
            ParagraphStyle newStyle = ParagraphStyle.findType(text);
            if (newStyle != ParagraphStyle.Paragraph) setParagraphStyle(index, newStyle);
        } else if (!style.matches(text)) {
            ParagraphStyle newStyle = ParagraphStyle.findType(text);
            setParagraphStyle(index, newStyle);
        }
    }

    private boolean hasSyntaxForParagraphStyle(int paragraphIndex, ParagraphStyle style) {
        String text = getParagraphText(paragraphIndex);
        return style.matches(text);
    }

    private void removeSyntaxForParagraphStyle(int paragraphIndex, ParagraphStyle style) {
        String text = getParagraphText(paragraphIndex);
        int end = style.getMarkdownSyntax(text).length();
        deleteText(paragraphIndex, 0, paragraphIndex, end);
    }

    private void addSyntaxForParagraphStyle(int paragraphIndex, ParagraphStyle style) {
        HiddenMarkdownSegment segment = new HiddenMarkdownSegment(style.createMarkdownSyntax());
        replace(paragraphIndex, 0, paragraphIndex, 0, ReadOnlyStyledDocument.fromSegment(eitherWrap(segment), style, getInitialTextStyle(), EITHER_OPS));
    }

    private ParagraphStyle getParagraphType(int index) {
        return getParagraph(index).getParagraphStyle();
    }

    public String getParagraphText(int index) {
        return getParagraph(index).getText();
    }

    private Either<TextSegment, HiddenMarkdownSegment> eitherWrap(HiddenMarkdownSegment segment) {
        return Either.right(segment);
    }
}

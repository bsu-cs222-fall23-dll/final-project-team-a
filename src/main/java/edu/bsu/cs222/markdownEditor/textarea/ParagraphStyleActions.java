package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.RenderedMarkdownSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import org.fxmisc.richtext.EditActions;
import org.fxmisc.richtext.StyleActions;
import org.reactfx.util.Either;

public interface ParagraphStyleActions extends StyleActions<ParagraphStyle, TextStyle>,
        EditActions<ParagraphStyle, Either<TextSegment, RenderedMarkdownSegment>, TextStyle> {

    default void checkParagraphStyleAgainstSyntax(int paragraphIndex) {
        ParagraphStyle currentStyle = getParagraphStyle(paragraphIndex);
        String text = getParagraphText(paragraphIndex);
        ParagraphStyle styleAccordingToSyntax = ParagraphStyle.findType(text);
        if (currentStyle != styleAccordingToSyntax)
            setParagraphStyle(paragraphIndex, styleAccordingToSyntax);
    }

    default void setParagraphStyleWithSyntax(int paragraphIndex, ParagraphStyle paragraphStyle) {
        ParagraphStyle currentStyle = getParagraphStyle(paragraphIndex);
        removeParagraphStyleSyntax(paragraphIndex, currentStyle);
        setParagraphStyle(paragraphIndex, paragraphStyle);
        addParagraphStyleSyntax(paragraphIndex, paragraphStyle);
    }

    default ParagraphStyle getParagraphStyle(int index) {
        return getParagraph(index).getParagraphStyle();
    }

    default String getParagraphText(int index) {
        return getParagraph(index).getText();
    }

    private void removeParagraphStyleSyntax(int paragraphIndex, ParagraphStyle style) {
        if (style == ParagraphStyle.Paragraph) return;
        String text = getParagraphText(paragraphIndex);
        if (style.matches(text)) {
            int end = style.getMarkdownSyntax(text).length();
            deleteText(paragraphIndex, 0, paragraphIndex, end);
        }
    }

    private void addParagraphStyleSyntax(int paragraphIndex, ParagraphStyle style) {
        if (style == ParagraphStyle.Paragraph) return;
        TextSegment segment = new TextSegment(style.createMarkdownSyntax());
        TextStyle markdownStyle = TextStyle.EMPTY.add(TextStyle.Property.Markdown);
        replace(paragraphIndex, 0, paragraphIndex, 0, Either.left(segment), markdownStyle);
    }

}

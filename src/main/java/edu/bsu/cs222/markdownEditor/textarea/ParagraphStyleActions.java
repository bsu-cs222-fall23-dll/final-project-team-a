package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.ParagraphSyntaxType;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import org.fxmisc.richtext.EditActions;
import org.fxmisc.richtext.StyleActions;

public interface ParagraphStyleActions extends StyleActions<ParagraphStyle, TextStyle>,
        EditActions<ParagraphStyle, Segment, TextStyle> {

    default ParagraphStyle getParagraphStyle(int index) {
        return getParagraph(index).getParagraphStyle();
    }

    default String getParagraphText(int index) {
        return getParagraph(index).getText();
    }

    default void setCurrentParagraphSyntax(ParagraphSyntaxType type) {
        int paragraphIndex = getCurrentParagraph();
        removeParagraphStyleSyntax(paragraphIndex);
        setParagraphStyle(paragraphIndex, type.getStyle());
        addParagraphSyntax(paragraphIndex, type);
    }

    private void removeParagraphStyleSyntax(int paragraphIndex) {
        ParagraphStyle style = getParagraphStyle(paragraphIndex);
        if (style == ParagraphStyle.Paragraph) return;
        ParagraphSyntaxType syntaxType = style.getSyntaxType();
        String text = getParagraphText(paragraphIndex);
        assert syntaxType != null;
        if (syntaxType.matches(text)) {
            int end = syntaxType.getSyntaxLength(text);
            deleteText(paragraphIndex, 0, paragraphIndex, end);
        }
    }

    private void addParagraphSyntax(int paragraphIndex, ParagraphSyntaxType type) {
        Segment segment = type.createReference().getMarkdownSegments().get(0);
        replace(paragraphIndex, 0, paragraphIndex, 0, segment, null);
    }

}

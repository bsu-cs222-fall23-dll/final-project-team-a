package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import javafx.scene.control.IndexRange;
import org.fxmisc.richtext.EditActions;
import org.fxmisc.richtext.NavigationActions;
import org.fxmisc.richtext.StyleActions;
import org.fxmisc.richtext.model.StyleSpans;

public interface TextStyleActions extends StyleActions<ParagraphStyle, TextStyle>,
        NavigationActions<ParagraphStyle, Segment, TextStyle>,
        EditActions<ParagraphStyle, Segment, TextStyle> {

    default void styleSelectedText(TextStyle.Property style) {
        int paragraph = getCurrentParagraph();
        String selectedText = getSelectedText();
        if (selectedText.isEmpty()) {
            int caretPosition = getCaretColumn();
            insertText(paragraph, caretPosition, style.defaultTagSyntax + style.defaultTagSyntax);
            moveTo(caretPosition + style.defaultTagSyntax.length());
        } else {
            IndexRange selectionRange = getParagraphSelection(getCurrentParagraph());
            insertText(paragraph, selectionRange.getStart(), style.defaultTagSyntax);
            int selectionRangeEnd = selectionRange.getEnd() + style.defaultTagSyntax.length();
            insertText(paragraph, selectionRangeEnd, style.defaultTagSyntax);
        }
    }

    default void overlayStyleSpans(int paragraphIndex, int start, StyleSpans<TextStyle> styleSpans) {
        StyleSpans<TextStyle> oldStyleSpans = getStyleSpans(paragraphIndex, start, start + styleSpans.length());
        StyleSpans<TextStyle> newStyleSpans = oldStyleSpans.overlay(styleSpans, TextStyle::overlay);
        setStyleSpans(paragraphIndex, start, newStyleSpans);
    }
}

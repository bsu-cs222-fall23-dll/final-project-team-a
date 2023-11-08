package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.RenderedMarkdownSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import javafx.scene.control.IndexRange;
import org.fxmisc.richtext.EditActions;
import org.fxmisc.richtext.NavigationActions;
import org.fxmisc.richtext.StyleActions;
import org.fxmisc.richtext.model.StyleSpans;
import org.reactfx.util.Either;

import java.util.Collection;

public interface TextStyleActions extends StyleActions<ParagraphStyle, TextStyle>,
        NavigationActions<ParagraphStyle, Either<TextSegment, RenderedMarkdownSegment>, TextStyle>,
        EditActions<ParagraphStyle, Either<TextSegment, RenderedMarkdownSegment>, TextStyle> {

    default void addStyleProperty(int paragraph, int from, int to, TextStyle.Property properties) {
        StyleSpans<TextStyle> newSpans = getStyleSpans(paragraph, from, to).mapStyles(span -> span.add(properties));
        setStyleSpans(paragraph, from, newSpans);
    }

    default void addStyleProperties(int paragraph, int from, int to, Collection<TextStyle.Property> properties) {
        StyleSpans<TextStyle> newSpans = getStyleSpans(paragraph, from, to).mapStyles(span -> span.addAll(properties));
        setStyleSpans(paragraph, from, newSpans);
    }

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
}

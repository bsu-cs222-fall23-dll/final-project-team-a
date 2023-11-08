package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.scene.Node;
import javafx.scene.control.IndexRange;
import org.fxmisc.flowless.VirtualizedScrollPane;

public class Editor {

    private final MarkdownTextArea textArea;

    public Editor() {
        textArea = new MarkdownTextArea();
    }

    public Node getNode() {
        return new VirtualizedScrollPane<>(textArea);
    }

    public String getContent() {
        return textArea.getText();
    }

    public void setContent(String content) {
        textArea.insertText(0, content);
    }

    public void setCurrentParagraphStyle(ParagraphStyle style) {
        int paragraphIndex = textArea.getCurrentParagraph();
        textArea.setParagraphStyleWithSyntax(paragraphIndex, style);
    }

    public void styleSelectedText(TextStyle.Property style) {
        int paragraph = textArea.getCurrentParagraph();
        String selectedText = textArea.getSelectedText();
        if (selectedText.isEmpty()) {
            int caretPosition = textArea.getCaretColumn();
            textArea.insertText(paragraph, caretPosition, style.defaultTagSyntax + style.defaultTagSyntax);
            textArea.moveTo(caretPosition + style.defaultTagSyntax.length());
        } else {
            IndexRange selectionRange = textArea.getParagraphSelection(textArea.getCurrentParagraph());
            textArea.insertText(paragraph, selectionRange.getStart(), style.defaultTagSyntax);
            int selectionRangeEnd = selectionRange.getEnd() + style.defaultTagSyntax.length();
            textArea.insertText(paragraph, selectionRangeEnd, style.defaultTagSyntax);
        }


    }
}

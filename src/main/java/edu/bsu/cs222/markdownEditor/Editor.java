package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.MarkdownSegment;
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
        textArea.setCurrentParagraphStyle(style);
    }

    public void styleSelectedText(TextStyle.Property style){
        int paragraph = textArea.getCurrentParagraph();
        String selectedText = textArea.getSelectedText();

        if (selectedText.isEmpty()) {
            int caretPosition = textArea.getCaretColumn();
            MarkdownSegment segment = new MarkdownSegment(style.defaultTagSyntax + style.defaultTagSyntax);
            textArea.insertMarkdown(paragraph, caretPosition, segment);
            textArea.moveTo(caretPosition + style.defaultTagSyntax.length());
        } else {
            IndexRange selectionRange = textArea.getParagraphSelection(textArea.getCurrentParagraph());
            MarkdownSegment openTagSegment = new MarkdownSegment(style.defaultTagSyntax);
            MarkdownSegment closeTagSegment = new MarkdownSegment(style.defaultTagSyntax);
            textArea.insertMarkdown(paragraph, selectionRange.getStart(), openTagSegment);
            int selectionRangeEnd = selectionRange.getEnd() + style.defaultTagSyntax.length();
            textArea.insertMarkdown(paragraph, selectionRangeEnd, closeTagSegment);
        }



    }
}

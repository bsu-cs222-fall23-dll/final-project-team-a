package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.scene.Node;
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
        textArea.styleSelectedText(style);
    }
}

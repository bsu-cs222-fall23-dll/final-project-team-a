package edu.bsu.cs222.markdownEditor;

import javafx.scene.Node;
import org.fxmisc.flowless.VirtualizedScrollPane;

public class Editor {

    private final MarkdownTextArea textArea;

    public Editor() {
        textArea = new MarkdownTextArea();
        new EventManager(this).initialize();
    }

    public Node getNode() {
        return new VirtualizedScrollPane<>(textArea);
    }

    public MarkdownTextArea getTextArea() {
        return textArea;
    }

    public String getContent() {
        return textArea.getText();
    }

    public void setContent(String content) {
        textArea.insertText(0, content);
    }

    public void checkParagraphType(int index) {
        MarkdownBlockType blockType = textArea.getParagraphStyle(index);
        String text = textArea.getParagraphText(index);
        if (blockType == MarkdownBlockType.Paragraph || !blockType.matches(text)) {
            MarkdownBlockType newStyle = MarkdownBlockType.findType(text);
            textArea.setParagraphStyle(index, newStyle);
        }
    }
}

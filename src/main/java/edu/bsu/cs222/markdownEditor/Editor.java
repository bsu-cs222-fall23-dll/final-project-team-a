package edu.bsu.cs222.markdownEditor;

import javafx.scene.Node;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.model.Paragraph;

import java.util.Collection;

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
        Paragraph<MarkdownBlockType, String, Collection<String>> paragraph = textArea.getParagraph(index);
        MarkdownBlockType blockType = paragraph.getParagraphStyle();
        if (blockType == MarkdownBlockType.Paragraph || !blockType.matches(paragraph.getText())) {
            MarkdownBlockType newStyle = MarkdownBlockType.findType(paragraph.getText());
            textArea.setParagraphStyle(index, newStyle);
        }
    }
}

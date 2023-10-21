package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyleClassedTextArea;

public class EditorController {

    @FXML
    private VBox editor;

    public String getContent() {
        StyleClassedTextArea textArea = (StyleClassedTextArea) editor.getChildren().get(0);
        return textArea.getText();
    }

    public void setContent(String content) {
        StyleClassedTextArea textArea = (StyleClassedTextArea) editor.getChildren().get(0);
        textArea.replaceText(content);
    }

    @FXML
    private void initialize() {
        editor.getChildren().add(MarkdownBlock.create(this));
    }

    public int getBlockIndex(CodeArea block) {
        return editor.getChildren().indexOf(block);
    }

    public CodeArea getBlockAt(int index) {
        return (CodeArea) editor.getChildren().get(index);
    }

    public void createBlock(int index) {
        CodeArea block = MarkdownBlock.create(this);
        editor.getChildren().add(index, block);
    }

    public void removeBlock(CodeArea block) {
        editor.getChildren().remove(block);
    }

}

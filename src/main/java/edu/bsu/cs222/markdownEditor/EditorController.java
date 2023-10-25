package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
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

    public int getBlockIndex(StyleClassedTextArea styleClassedTextArea) {
        return editor.getChildren().indexOf(styleClassedTextArea);
    }

    public StyleClassedTextArea getBlockAt(int index) {
        return (StyleClassedTextArea) editor.getChildren().get(index);
    }

    public void createBlock(int index) {
        StyleClassedTextArea styleClassedTextArea = MarkdownBlock.create(this);
        editor.getChildren().add(index, styleClassedTextArea);
    }

    public void removeBlock(StyleClassedTextArea styleClassedTextArea) {
        editor.getChildren().remove(styleClassedTextArea);
    }

}

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
        editor.getChildren().addAll(
                MarkdownBlock.create(),
                MarkdownBlock.create()
        );
    }
}

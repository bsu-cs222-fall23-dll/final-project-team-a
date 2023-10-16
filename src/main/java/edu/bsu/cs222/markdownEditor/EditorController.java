package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class EditorController {

    @FXML
    private TextArea editor;

    public String getContent() {
        return editor.getText();
    }

    public void setContent(String content) {
        editor.setText(content);
    }

}

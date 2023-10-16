package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;

public class AppController {
    @FXML
    private MenuBarController menuBarController;

    @FXML
    private EditorController editorController;

    @FXML
    private void initialize() {
        menuBarController.setEditorController(editorController);
    }
}

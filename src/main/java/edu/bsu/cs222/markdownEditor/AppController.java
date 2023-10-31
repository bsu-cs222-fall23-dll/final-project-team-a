package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AppController {
    @FXML
    private VBox app;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private void initialize() {
        MarkdownBlock markdownBlock = new MarkdownBlock();
        app.getChildren().add(markdownBlock);
        menuBarController.setMarkdownBlock(markdownBlock);
    }
}

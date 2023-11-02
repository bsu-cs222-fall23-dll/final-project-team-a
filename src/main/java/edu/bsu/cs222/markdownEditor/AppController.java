package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AppController {
    private final Editor editor = new Editor();
    @FXML
    private VBox appContainer;
    @FXML
    private MenuBarController menuBarController;

    @FXML
    private void initialize() {
        Node node = editor.getNode();
        appContainer.getChildren().add(node);
        VBox.setVgrow(node, Priority.ALWAYS);
        menuBarController.setMarkdownEditor(editor);
    }
}

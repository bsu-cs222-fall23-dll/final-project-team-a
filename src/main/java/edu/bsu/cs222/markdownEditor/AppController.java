package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AppController {
    private final MarkdownTextArea textArea = new MarkdownTextArea();
    @FXML
    private VBox appContainer;
    @FXML
    private MenuBarController menuBarController;

    @FXML
    private void initialize() {
        appContainer.getChildren().add(textArea);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        menuBarController.setTextArea(textArea);
    }
}
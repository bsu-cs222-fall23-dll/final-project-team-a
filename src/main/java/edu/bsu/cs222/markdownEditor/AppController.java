package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;

public class AppController {
    @FXML
    private VBox appContainer;

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private void initialize() {
        MarkdownEditor markdownEditor = new MarkdownEditor();
        VirtualizedScrollPane<MarkdownEditor> vsPane = new VirtualizedScrollPane<>(markdownEditor);
        VBox.setVgrow(vsPane, Priority.ALWAYS);
        appContainer.getChildren().add(vsPane);
        menuBarController.setMarkdownBlock(markdownEditor);
    }
}

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
        MarkdownTextArea textArea = new MarkdownTextArea();
        VirtualizedScrollPane<MarkdownTextArea> vsPane = new VirtualizedScrollPane<>(textArea);
        VBox.setVgrow(vsPane, Priority.ALWAYS);
        appContainer.getChildren().add(vsPane);
        menuBarController.setMarkdownEditor(textArea);
    }
}

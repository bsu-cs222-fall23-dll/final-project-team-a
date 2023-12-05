package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;

public class AppController {
    private Scene scene;

    private final FileManager fileManager = new FileManager(null);

    private final MarkdownTextArea textArea = new MarkdownTextArea();
    @FXML
    private VBox appContainer;
    @FXML
    private MenuBarController menuBarController;

    @FXML
    private void initialize() {
        appContainer.getChildren().add(textArea);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        menuBarController.setFileManager(fileManager);
        menuBarController.setTextArea(textArea);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void loadFont(URL fontUrl) {
        Font.loadFont(fontUrl.toExternalForm(), 10);
    }

    public void loadCss(URL cssUrl) {
        scene.getStylesheets().add(cssUrl.toExternalForm());
    }
}
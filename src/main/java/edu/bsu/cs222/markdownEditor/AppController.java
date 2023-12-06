package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;

public class AppController {
    private Scene scene;
    private Window window;

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
        menuBarController.setAppController(this);
        menuBarController.setFileManager(fileManager);
        menuBarController.setTextArea(textArea);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.window = scene.getWindow();
    }

    public void loadFont(URL fontUrl) {
        Font.loadFont(fontUrl.toExternalForm(), 10);
    }

    public void loadCss(URL cssUrl) {
        scene.getStylesheets().add(cssUrl.toExternalForm());
    }

    public void createModal(Scene scene) {
        Stage modal = new Stage();
        modal.setScene(scene);
        modal.initOwner(window);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.showAndWait();
    }
}
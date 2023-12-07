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

    private Window window;
    private final URL systemCss = Main.getResourceUrl("/style.css");

    final FileManager fileManager = new FileManager(null, this);
    private final EventManager eventManager;

    final MarkdownTextArea textArea = new MarkdownTextArea();
    @FXML
    private VBox appContainer;
    @FXML
    MenuBarController menuBarController;

    public AppController() {
        eventManager = new EventManager(textArea, fileManager);
    }

    public void setScene(Scene scene) {
        this.window = scene.getWindow();
        setSystemStyle();
        setEditorStyle();
    }

    public void setFontFamily(String family) {
        textArea.setStyle("-fx-font-family: '" + family + "'");
    }

    public void setEditorCss(URL cssUrl) {
        textArea.getStylesheets().clear();
        textArea.getStylesheets().add(cssUrl.toExternalForm());
    }

    public void clearText(){
        textArea.clear();
    }

    public void createModal(Scene scene) {
        scene.getStylesheets().add(systemCss.toExternalForm());
        Stage modal = new Stage();
        modal.setScene(scene);
        modal.initOwner(window);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.showAndWait();
    }

    @FXML
    private void initialize() {
        eventManager.initialize();
        appContainer.getChildren().add(textArea);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        menuBarController.setAppController(this);
        menuBarController.setFileManager(fileManager);
        menuBarController.setTextArea(textArea);
    }

    private void setSystemStyle() {
        URL fontUrl = Main.getResourceUrl("/fonts/SourceCodePro.ttf");
        Font.loadFont(fontUrl.toExternalForm(), 10);
        setEditorCss(systemCss);
    }

    private void setEditorStyle() {
        setEditorCss(Main.getResourceUrl("/styles/default.css"));
        setFontFamily(UserPreferences.FontFamily.getValue());
    }
}
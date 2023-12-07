package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class WarningModalController {
    private FileManager fileManager;
    private MarkdownTextArea textArea;
    private MenuBarController menuBarController;

    public void initialize(AppController appController){
        this.textArea = appController.textArea;
        this.fileManager = appController.fileManager;
        this.menuBarController = appController.menuBarController;
    }

    @FXML
    private Button closeButton;

    @FXML
    private Button saveAndCloseButton;

    @FXML
    private void closeButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        textArea.clear();
    }
    @FXML
    public void SaveAndClose() {
        Stage stage = (Stage) saveAndCloseButton.getScene().getWindow();
        stage.close();
        try {
            fileManager.save(textArea.getText());
            textArea.clear();
        } catch (NoFileOpenException e) {
            menuBarController.saveFileAs();
            textArea.clear();
        }
    }
}



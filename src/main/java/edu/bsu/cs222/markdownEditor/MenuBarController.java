package edu.bsu.cs222.markdownEditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuBarController {

    private EditorController editorController;

    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    private FileChooser fileChooser = new FileChooser();

    @FXML
    private void newFile(ActionEvent actionEvent) {
    }

    @FXML
    private void openFile(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(null);
        App.fileManager.open(file);
        String content = null;
        try {
            content = App.fileManager.getActiveFileContents();
        } catch (NoFileOpenException e) {
            throw new RuntimeException(e);
        }
        editorController.setContent(content);
    }

    @FXML
    private void saveFile(ActionEvent actionEvent) {
        try {
            App.fileManager.save(editorController.getContent());
        } catch (NoFileOpenException e) {
            // TODO: Handle NoActiveFileException
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void saveFileAs(ActionEvent actionEvent) {
        File file = fileChooser.showSaveDialog(null);
        App.fileManager.saveAs(editorController.getContent(), file);
    }
}

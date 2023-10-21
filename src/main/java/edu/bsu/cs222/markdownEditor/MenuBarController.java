package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuBarController {

    private EditorController editorController;

    public void setEditorController(EditorController editorController) {
        this.editorController = editorController;
    }

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void newFile() {
    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(null);
        String content = Main.fileManager.open(file);
        editorController.setContent(content);
    }

    @FXML
    private void saveFile() {
        try {
            Main.fileManager.save(editorController.getContent());
        } catch (NoFileOpenException e) {
            saveFileAs();
        }
    }

    @FXML
    private void saveFileAs() {
        File file = fileChooser.showSaveDialog(null);
        Main.fileManager.saveAs(editorController.getContent(), file);
    }
}

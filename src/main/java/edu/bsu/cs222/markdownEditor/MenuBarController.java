package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuBarController {

    private Editor editor;

    public void setMarkdownEditor(Editor editor) {
        this.editor = editor;
    }

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void newFile() {
    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(null);
        String content = Main.fileManager.open(file);
        editor.setContent(content);
    }

    @FXML
    private void saveFile() {
        try {
            Main.fileManager.save(editor.getContent());
        } catch (NoFileOpenException e) {
            saveFileAs();
        }
    }

    @FXML
    private void saveFileAs() {
        File file = fileChooser.showSaveDialog(null);
        Main.fileManager.saveAs(editor.getContent(), file);
    }
}

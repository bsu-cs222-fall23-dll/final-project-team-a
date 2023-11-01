package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuBarController {

    private MarkdownEditor markdownEditor;

    public void setMarkdownEditor(MarkdownEditor markdownEditor) {
        this.markdownEditor = markdownEditor;
    }

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void newFile() {
    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(null);
        String content = Main.fileManager.open(file);
        markdownEditor.insertText(0, content);
    }

    @FXML
    private void saveFile() {
        try {
            Main.fileManager.save(markdownEditor.getText());
        } catch (NoFileOpenException e) {
            saveFileAs();
        }
    }

    @FXML
    private void saveFileAs() {
        File file = fileChooser.showSaveDialog(null);
        Main.fileManager.saveAs(markdownEditor.getText(), file);
    }
}

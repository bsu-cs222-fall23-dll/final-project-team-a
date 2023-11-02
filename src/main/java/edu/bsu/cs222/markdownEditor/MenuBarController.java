package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuBarController {

    private MarkdownTextArea textArea;

    public void setMarkdownEditor(MarkdownTextArea textArea) {
        this.textArea = textArea;
    }

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void newFile() {
    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(null);
        String content = Main.fileManager.open(file);
        textArea.insertText(0, content);
    }

    @FXML
    private void saveFile() {
        try {
            Main.fileManager.save(textArea.getText());
        } catch (NoFileOpenException e) {
            saveFileAs();
        }
    }

    @FXML
    private void saveFileAs() {
        File file = fileChooser.showSaveDialog(null);
        Main.fileManager.saveAs(textArea.getText(), file);
    }
}

package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuBarController {

    private MarkdownBlock markdownBlock;

    public void setMarkdownBlock(MarkdownBlock markdownBlock) {
        this.markdownBlock = markdownBlock;
    }

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void newFile() {
    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(null);
        String content = Main.fileManager.open(file);
        markdownBlock.insertText(0, content);
    }

    @FXML
    private void saveFile() {
        try {
            Main.fileManager.save(markdownBlock.getText());
        } catch (NoFileOpenException e) {
            saveFileAs();
        }
    }

    @FXML
    private void saveFileAs() {
        File file = fileChooser.showSaveDialog(null);
        Main.fileManager.saveAs(markdownBlock.getText(), file);
    }
}

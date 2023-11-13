package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.parser.ParagraphSyntaxType;
import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuBarController {

    private final FileChooser fileChooser = new FileChooser();
    private MarkdownTextArea textArea;

    public void setTextArea(MarkdownTextArea texArea) {
        this.textArea = texArea;
    }

    @FXML
    public void italicsButton() {
        textArea.styleSelectedText(TextStyle.Property.Italics);

    }

    @FXML
    private void newFile() {
    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(null);
        String content = Main.fileManager.open(file);
        textArea.setText(content);
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

    @FXML
    private void header1Button() {
        textArea.setCurrentParagraphSyntax(ParagraphSyntaxType.Heading1);
    }

    @FXML
    private void header2Button() {
        textArea.setCurrentParagraphSyntax(ParagraphSyntaxType.Heading2);
    }

    @FXML
    private void header3Button() {
        textArea.setCurrentParagraphSyntax(ParagraphSyntaxType.Heading3);
    }

    @FXML
    private void boldButton() {
        textArea.styleSelectedText(TextStyle.Property.Bold);
    }

    @FXML
    private void inlineCode() {
        textArea.styleSelectedText(TextStyle.Property.Code);
    }

}

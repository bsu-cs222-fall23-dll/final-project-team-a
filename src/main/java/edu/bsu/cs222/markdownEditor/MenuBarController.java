package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.parser.ParagraphSyntaxType;
import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class MenuBarController {

    private AppController appController;
    private final FileChooser fileChooser = new FileChooser();
    private FileManager fileManager;
    private MarkdownTextArea textArea;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setTextArea(MarkdownTextArea texArea) {
        this.textArea = texArea;
    }

    @FXML
    public void italicsButton() {
        textArea.styleSelectedText(TextStyle.Property.Italics);

    }

    @FXML
    private void newFile() {
        fileManager.newFile();
    }

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(null);
        String content = fileManager.open(file);
        textArea.setText(content);
    }

    @FXML
    private void saveFile() {
        try {
            fileManager.save(textArea.getText());
        } catch (NoFileOpenException e) {
            saveFileAs();
        }
    }

    @FXML
    void saveFileAs() {
        File file = fileChooser.showSaveDialog(null);
        fileManager.saveAs(textArea.getText(), file);
    }

    @FXML
    private void openPreferences() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.getResourceUrl("/preferences.fxml"));
        try {
            Scene preferencesScene = fxmlLoader.load();
            PreferenceController preferenceController = fxmlLoader.getController();
            preferenceController.setAppController(appController);
            appController.createModal(preferencesScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    private void numberedListButton() {
        textArea.setCurrentParagraphSyntax(ParagraphSyntaxType.OrderedList);
    }

    @FXML
    private void bulletPointsButton() {
        textArea.setCurrentParagraphSyntax(ParagraphSyntaxType.UnorderedList);
    }

    @FXML
    private void boldButton() {
        textArea.styleSelectedText(TextStyle.Property.Bold);
    }

    @FXML
    private void inlineCode() {
        textArea.styleSelectedText(TextStyle.Property.Code);
    }

    @FXML
    private void highlightButton(){
        textArea.styleSelectedText(TextStyle.Property.Highlight);
    }

}

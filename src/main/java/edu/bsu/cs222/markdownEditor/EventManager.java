package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.textarea.MarkdownTextArea;
import javafx.beans.value.ObservableValue;

class EventManager {

    private final MarkdownTextArea textArea;
    private final FileManager fileManager;

    EventManager(MarkdownTextArea textArea, FileManager fileManager) {
        this.textArea = textArea;
        this.fileManager = fileManager;
    }

    public void initialize() {
        textArea.textProperty().addListener(this::handleTextChange);
        textArea.currentParagraphProperty().addListener(this::handleCurrentParagraphChange);
    }

    private void handleTextChange(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        int currentParagraph = textArea.getCurrentParagraph();
        textArea.showParagraphMarkdown(currentParagraph);
    }

    private void handleCurrentParagraphChange(ObservableValue<? extends Integer> observableValue,
            Integer oldValue,
            Integer newValue) {
        if (oldValue < textArea.getParagraphs().size())
            textArea.showParagraphRender(oldValue);
        textArea.showParagraphMarkdown(newValue);
    }
}

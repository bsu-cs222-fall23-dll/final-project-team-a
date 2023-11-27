package edu.bsu.cs222.markdownEditor.textarea;

import javafx.beans.value.ObservableValue;

class EventManager {

    private final MarkdownTextArea textArea;

    EventManager(MarkdownTextArea textArea) {
        this.textArea = textArea;
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
        textArea.showParagraphRender(oldValue);
        textArea.showParagraphMarkdown(newValue);
    }
}

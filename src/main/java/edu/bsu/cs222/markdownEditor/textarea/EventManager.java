package edu.bsu.cs222.markdownEditor.textarea;

import javafx.beans.value.ObservableValue;

class EventManager {

    private final MarkdownTextArea textArea;

    EventManager(MarkdownTextArea textArea) {
        this.textArea = textArea;
    }

    public void initialize() {
        textArea.textProperty().addListener(this::handleTextChange);
    }


    private void handleTextChange(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        textArea.checkCurrentParagraphType();
    }
}

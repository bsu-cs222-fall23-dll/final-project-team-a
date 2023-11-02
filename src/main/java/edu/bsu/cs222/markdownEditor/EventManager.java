package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ObservableValue;

class EventManager {

    private final Editor editor;
    private final MarkdownTextArea textArea;

    EventManager(Editor editor) {
        this.editor = editor;
        this.textArea = editor.getTextArea();
    }

    public void initialize() {
        textArea.textProperty().addListener(this::handleTextChange);
    }


    private void handleTextChange(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        int index = textArea.getCurrentParagraph();
        editor.checkParagraphType(index);
    }
}

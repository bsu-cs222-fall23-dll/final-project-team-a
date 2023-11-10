package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.MarkdownParser;
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
        styleMarkdownSyntaxInParagraph(currentParagraph);
        textArea.checkParagraphStyleAgainstSyntax(currentParagraph);
    }

    private void handleCurrentParagraphChange(ObservableValue<? extends Integer> observableValue,
            Integer oldValue,
            Integer newValue) {
        textArea.hideParagraphMarkdown(oldValue);
        textArea.showParagraphMarkdown(newValue);
    }

    private void styleMarkdownSyntaxInParagraph(int currentParagraph) {
        textArea.clearStyle(currentParagraph);
        String text = textArea.getParagraphText(currentParagraph);
        MarkdownParser parser = new MarkdownParser(text);
        parser.getMarkdownSyntax().forEach(syntaxReference ->
                textArea.setStyleSpans(currentParagraph,
                        syntaxReference.range.getStart(),
                        syntaxReference.getStyleSpans())
        );
    }
}

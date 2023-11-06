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
        textArea.checkCurrentParagraphStyle();
        styleInlineMarkdown();
    }


    private void styleInlineMarkdown() {
        int currentParagraph = textArea.getCurrentParagraph();
        textArea.clearStyle(currentParagraph);
        String text = textArea.getParagraphText(currentParagraph);
        for (InlineMarkdown inlineStyle : InlineMarkdown.values()) {
            inlineStyle.forEachReference(text, matcher -> {
                textArea.replaceWithMarkdown(currentParagraph, matcher.start(1), matcher.end(1));
                textArea.replaceWithMarkdown(currentParagraph, matcher.start(2), matcher.end(2));
                textArea.addStyle(currentParagraph, matcher.start(), matcher.end(), inlineStyle.style);
            });
        }
    }
}

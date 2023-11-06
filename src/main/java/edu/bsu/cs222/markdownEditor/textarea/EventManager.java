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
        textArea.clearParagraphStyle(currentParagraph);
        String text = textArea.getParagraphText(currentParagraph);
        for (InlineMarkdown inlineStyle : InlineMarkdown.values()) {
            inlineStyle.forEachReference(text, matcher -> {
                textArea.replaceWithMarkdown(currentParagraph, matcher.start("openTag"), matcher.end("openTag"));
                textArea.replaceWithMarkdown(currentParagraph, matcher.start("closeTag"), matcher.end("closeTag"));
                textArea.addStyle(currentParagraph, matcher.start(), matcher.end(), inlineStyle.styleProperty);
            });
        }
    }
}

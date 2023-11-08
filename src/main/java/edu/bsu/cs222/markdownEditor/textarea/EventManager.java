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
        styleInlineMarkdown(currentParagraph);
        textArea.checkParagraphStyleAgainstSyntax(currentParagraph);
    }

    private void handleCurrentParagraphChange(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
        textArea.hideParagraphMarkdown(oldValue);
        textArea.showParagraphMarkdown(newValue);
    }


    private void styleInlineMarkdown(int currentParagraph) {
        textArea.clearStyle(currentParagraph);
        String text = textArea.getParagraphText(currentParagraph);
        TextStyle markdownStyle = TextStyle.EMPTY.add(TextStyle.Property.Markdown);
        for (InlineMarkdown inlineStyle : InlineMarkdown.values()) {
            inlineStyle.forEachReference(text, matcher -> {
                textArea.addStyle(currentParagraph, matcher.start(1), matcher.end(1), markdownStyle);
                textArea.addStyle(currentParagraph, matcher.start(2), matcher.end(2), markdownStyle);
                textArea.addStyle(currentParagraph, matcher.start(), matcher.end(), inlineStyle.style);
            });
        }
    }
}

package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.MarkdownParser;
import edu.bsu.cs222.markdownEditor.parser.syntax.TagWrappedSyntaxReference;
import javafx.beans.value.ObservableValue;

import java.util.Set;

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
        parser.getMarkdownSyntax().forEach(syntaxReference -> {
            if (syntaxReference instanceof TagWrappedSyntaxReference tagWrappedSyntaxReference) {
                int start = tagWrappedSyntaxReference.range.getStart();
                int end = start + tagWrappedSyntaxReference.getTagLength();
                textArea.addStyleProperty(currentParagraph, start, end, TextStyle.Property.Markdown);
                start = end;
                end = start + tagWrappedSyntaxReference.getContentLength();
                Set<TextStyle.Property> properties = syntaxReference.getTextStyle().getProperties();
                textArea.addStyleProperties(currentParagraph, start, end, properties);
                start = end;
                end = start + tagWrappedSyntaxReference.getTagLength();
                textArea.addStyleProperty(currentParagraph, start, end, TextStyle.Property.Markdown);
            }
        });
    }
}

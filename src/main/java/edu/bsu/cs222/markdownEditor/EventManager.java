package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.segments.InlineMarkdown;
import javafx.beans.value.ObservableValue;

public class EventManager {
    private final MarkdownEditor block;

    EventManager(MarkdownEditor block) {
        this.block = block;
        block.textProperty().addListener(this::handleTextChange);
    }

    private void handleTextChange(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        InlineMarkdown.findInstances(block, newValue);
    }
}

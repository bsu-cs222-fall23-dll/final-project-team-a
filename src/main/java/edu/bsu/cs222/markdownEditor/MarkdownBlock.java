package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ChangeListener;
import org.fxmisc.richtext.CodeArea;

public class MarkdownBlock {

    private final CodeArea codeArea = new CodeArea();

    private MarkdownBlockType blockType = MarkdownBlockType.Paragraph;

    private final ChangeListener<String> textListener = (observable, oldValue, newValue) -> {
        if (blockType == MarkdownBlockType.Paragraph) {
            for (MarkdownBlockType blockType : MarkdownBlockType.values()) {
                if (blockType != MarkdownBlockType.Paragraph && newValue.matches(blockType.regexp)) {
                    this.blockType = blockType;
                    blockType.setStyle(codeArea);
                    break;
                }
            }
        } else if (!newValue.matches(blockType.regexp)) {
            blockType.removeStyle(codeArea);
            blockType = MarkdownBlockType.Paragraph;
        }
    };

    private MarkdownBlock() {
        if (codeArea.isFocused()) {
            codeArea.textProperty().addListener(textListener);
            codeArea.getStyleClass().add("focused");
        }
        codeArea.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (isFocused) {
                codeArea.textProperty().addListener(textListener);
                if (blockType != null) codeArea.getStyleClass().add("focused");
            } else {
                codeArea.textProperty().removeListener(textListener);
                if (blockType != null) codeArea.getStyleClass().remove("focused");
            }
        });
    }

    public static CodeArea create() {
        return new MarkdownBlock().codeArea;
    }
}

package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;

public class MarkdownBlock {

    private final EditorController editorController;

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
        overrideKeyPressEvent();
    };

    private MarkdownBlock(EditorController editorController) {
        this.editorController = editorController;
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

    public static CodeArea create(EditorController editorController) {
        return new MarkdownBlock(editorController).codeArea;
    }

    private void overrideKeyPressEvent() {
        InputMap<KeyEvent> overrides = InputMap.consume(keyPressed(KeyCode.ENTER));
        Nodes.addInputMap(codeArea, overrides);
        codeArea.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) handleEnterKeyPress();
        });
    }

    private void handleEnterKeyPress() {
        int currentIndex = editorController.getBlockIndex(codeArea);

        editorController.createBlock(currentIndex + 1);
        CodeArea newCodeArea = editorController.getBlockAt(currentIndex + 1);

        newCodeArea.requestFocus();
    }
}

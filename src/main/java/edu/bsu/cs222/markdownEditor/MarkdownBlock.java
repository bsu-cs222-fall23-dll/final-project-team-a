package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import static org.fxmisc.wellbehaved.event.EventPattern.anyOf;
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
        overrideKeyPressEvent();
    }

    public static CodeArea create(EditorController editorController) {
        return new MarkdownBlock(editorController).codeArea;
    }

    private void overrideKeyPressEvent() {
        InputMap<KeyEvent> overrides = InputMap.consume(anyOf(
                keyPressed(KeyCode.ENTER),
                keyPressed(KeyCode.BACK_SPACE),
                keyPressed(KeyCode.UP),
                keyPressed(KeyCode.DOWN)
        ));
        Nodes.addInputMap(codeArea, overrides);
        codeArea.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) handleEnterKeyPress();
            else if (event.getCode().equals(KeyCode.BACK_SPACE)) handleBackSpaceKeyPress();
            else if (event.getCode().equals(KeyCode.UP)) handleUpArrowKeyPress();
            else if (event.getCode().equals(KeyCode.DOWN)) handleDownArrowKeyPress();

        });
    }

    private void handleEnterKeyPress() {
        int currentIndex = editorController.getBlockIndex(codeArea);
        int start = codeArea.getCaretPosition(), end = codeArea.getLength();
        String content = codeArea.getText(start, end);

        editorController.createBlock(currentIndex + 1);
        CodeArea newCodeArea = editorController.getBlockAt(currentIndex + 1);

        if (!content.isEmpty()) {
            newCodeArea.insertText(0, content);
            codeArea.deleteText(start, end);
        }
        newCodeArea.requestFocus();
        newCodeArea.moveTo(0);
    }

    private void handleBackSpaceKeyPress() {
        int caretPosition = codeArea.getCaretPosition();
        int currentIndex = editorController.getBlockIndex(codeArea);
        if (caretPosition == 0 && currentIndex != 0) {
            editorController.removeBlock(codeArea);

            CodeArea lastCodeArea = editorController.getBlockAt(currentIndex - 1);
            String content = codeArea.getText();
            lastCodeArea.requestFocus();
            if (content != null && !content.isEmpty()) {
                lastCodeArea.insertText(lastCodeArea.getLength(), content);
                lastCodeArea.moveTo(lastCodeArea.getLength() - content.length());
            }
        } else codeArea.deletePreviousChar();
    }

    private void handleUpArrowKeyPress() {
        int currentIndex = editorController.getBlockIndex(codeArea);

        if (currentIndex != 0) {
            CodeArea aboveCurrentCodeArea = editorController.getBlockAt(currentIndex - 1);
            aboveCurrentCodeArea.requestFocus();

        } // else when they try to move up when at block 1
    }

    private void handleDownArrowKeyPress() {
        int endIndex = codeArea.getLength();
        int currentIndex = editorController.getBlockIndex(codeArea);

        if (currentIndex != endIndex) {
            CodeArea belowCurrentCodeArea = editorController.getBlockAt(currentIndex + 1);
            belowCurrentCodeArea.requestFocus();

        } // else when they try ot move down when at last block
        // Console throws out of bounds error - program still runs
    }
}

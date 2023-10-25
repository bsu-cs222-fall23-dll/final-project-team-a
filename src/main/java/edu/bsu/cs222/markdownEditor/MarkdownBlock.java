package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import static org.fxmisc.wellbehaved.event.EventPattern.anyOf;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;

public class MarkdownBlock {

    private final EditorController editorController;

    private final StyleClassedTextArea textArea = new StyleClassedTextArea();
    private MarkdownBlockType blockType = MarkdownBlockType.Paragraph;

    private final ChangeListener<String> textListener = (observable, oldValue, newValue) -> {
        if (blockType == MarkdownBlockType.Paragraph) {
            for (MarkdownBlockType blockType : MarkdownBlockType.values()) {
                if (blockType != MarkdownBlockType.Paragraph && newValue.matches(blockType.regexp)) {
                    this.blockType = blockType;
                    blockType.setStyle(textArea);
                    break;
                }
            }
        } else if (!newValue.matches(blockType.regexp)) {
            blockType.removeStyle(textArea);
            blockType = MarkdownBlockType.Paragraph;
        }
        clearInlineMarkdownStyle();
        styleInlineMarkdown();
    };

    private MarkdownBlock(EditorController editorController) {
        this.editorController = editorController;
        if (textArea.isFocused()) {
            textArea.textProperty().addListener(textListener);
            textArea.getStyleClass().add("focused");
        }
        textArea.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (isFocused) {
                textArea.textProperty().addListener(textListener);
                if (blockType != null) textArea.getStyleClass().add("focused");
            } else {
                textArea.textProperty().removeListener(textListener);
                if (blockType != null) textArea.getStyleClass().remove("focused");
            }
        });
        overrideKeyPressEvent();
    }

    private void styleInlineMarkdown() {
        for (InlineMarkdownType inlineMarkdownType : InlineMarkdownType.values()) {
            inlineMarkdownType.getOccurrences(textArea.getText()).forEach(inlineMarkdown -> {
                textArea.setStyleClass(inlineMarkdown.start, inlineMarkdown.end, inlineMarkdownType.className);
            });
        }
    }

    private void clearInlineMarkdownStyle() {
        int start = blockType.getMarkdownLength();
        int end = textArea.getLength();
        textArea.setStyleClass(start, end, "");
    }

    public static StyleClassedTextArea create(EditorController editorController) {
        return new MarkdownBlock(editorController).textArea;
    }

    private void overrideKeyPressEvent() {
        InputMap<KeyEvent> overrides = InputMap.consume(anyOf(
                keyPressed(KeyCode.ENTER),
                keyPressed(KeyCode.BACK_SPACE),
                keyPressed(KeyCode.UP),
                keyPressed(KeyCode.DOWN)
        ));
        Nodes.addInputMap(textArea, overrides);
        textArea.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) handleEnterKeyPress();
            else if (event.getCode().equals(KeyCode.BACK_SPACE)) handleBackSpaceKeyPress();
            else if (event.getCode().equals(KeyCode.UP)) handleUpArrowKeyPress();
            else if (event.getCode().equals(KeyCode.DOWN)) handleDownArrowKeyPress();

        });
    }

    private void handleEnterKeyPress() {
        int currentIndex = editorController.getBlockIndex(textArea);
        int start = textArea.getCaretPosition(), end = textArea.getLength();
        String content = textArea.getText(start, end);

        editorController.createBlock(currentIndex + 1);
        StyleClassedTextArea newCodeArea = editorController.getBlockAt(currentIndex + 1);

        if (!content.isEmpty()) {
            newCodeArea.insertText(0, content);
            textArea.deleteText(start, end);
        }
        newCodeArea.requestFocus();
        newCodeArea.moveTo(0);
    }

    private void handleBackSpaceKeyPress() {
        int caretPosition = textArea.getCaretPosition();
        int currentIndex = editorController.getBlockIndex(textArea);
        if (caretPosition == 0 && currentIndex != 0) {
            editorController.removeBlock(textArea);

            StyleClassedTextArea lastCodeArea = editorController.getBlockAt(currentIndex - 1);
            String content = textArea.getText();
            lastCodeArea.requestFocus();
            if (content != null && !content.isEmpty()) {
                lastCodeArea.insertText(lastCodeArea.getLength(), content);
                lastCodeArea.moveTo(lastCodeArea.getLength() - content.length());
            }
        } else textArea.deletePreviousChar();
    }

    private void handleUpArrowKeyPress() {
        int currentIndex = editorController.getBlockIndex(textArea);

        if (currentIndex != 0) {
            StyleClassedTextArea aboveCurrentCodeArea = editorController.getBlockAt(currentIndex - 1);
            aboveCurrentCodeArea.requestFocus();

        } // else when they try to move up when at block 1
    }

    private void handleDownArrowKeyPress() {
        int endIndex = textArea.getLength();
        int currentIndex = editorController.getBlockIndex(textArea);

        if (currentIndex != endIndex) {
            StyleClassedTextArea belowCurrentCodeArea = editorController.getBlockAt(currentIndex + 1);
            belowCurrentCodeArea.requestFocus();

        } // else when they try ot move down when at last block
        // Console throws out of bounds error - program still runs
    }
}

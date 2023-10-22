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
        overrideKeyPressEvent();
    };

    private MarkdownBlock(EditorController editorController) {
        this.editorController = editorController;
        if (codeArea.isFocused()) codeArea.textProperty().addListener(textListener);
        ChangeListener<Boolean> focusListener = (observable, oldValue, isFocused) -> handleFocusChange(isFocused);
        codeArea.focusedProperty().addListener(focusListener);
    }

    public static CodeArea create(EditorController editorController) {
        return new MarkdownBlock(editorController).codeArea;
    }

    private void handleFocusChange(boolean isFocused) {
        if (isFocused) {
            blockType.addMarkdown(codeArea);
            codeArea.textProperty().addListener(textListener);
        } else {
            codeArea.textProperty().removeListener(textListener);
            blockType.removeMarkdown(codeArea);
        }
    }

    private void overrideKeyPressEvent() {
        InputMap<KeyEvent> overrides = InputMap.consume(anyOf(keyPressed(KeyCode.ENTER), keyPressed(KeyCode.BACK_SPACE)));
        Nodes.addInputMap(codeArea, overrides);
        codeArea.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) handleEnterKeyPress();
            else if (event.getCode().equals(KeyCode.BACK_SPACE)) handleBackSpaceKeyPress();
        });
    }

    private void handleEnterKeyPress() {
        int currentIndex = editorController.getBlockIndex(codeArea);
        int start = codeArea.getCaretPosition(), end = codeArea.getLength();
        String content = codeArea.getText(start, end);

        editorController.createBlock(currentIndex + 1);
        CodeArea newCodeArea = editorController.getBlockAt(currentIndex + 1);

        newCodeArea.requestFocus();
        if (!content.isEmpty()) {
            newCodeArea.insertText(0, content);
            codeArea.deleteText(start, end);
            newCodeArea.moveTo(0);
        }
    }

    private void handleBackSpaceKeyPress() {
        int caretPosition = codeArea.getCaretPosition();
        int currentIndex = editorController.getBlockIndex(codeArea);
        if (caretPosition == 0 && currentIndex != 0) {
            editorController.removeBlock(codeArea);

            CodeArea lastCodeArea = editorController.getBlockAt(currentIndex - 1);
            lastCodeArea.requestFocus();
            String content = codeArea.getText();
            if (content != null && !content.isEmpty()) {
                lastCodeArea.insertText(lastCodeArea.getLength(), content);
                lastCodeArea.moveTo(lastCodeArea.getLength() - content.length());
            }
        } else codeArea.deletePreviousChar();
    }
}

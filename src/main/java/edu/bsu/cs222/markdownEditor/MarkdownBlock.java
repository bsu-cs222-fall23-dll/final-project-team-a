package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    };

    private MarkdownBlock(EditorController editorController) {
        this.editorController = editorController;
        if (textArea.isFocused()) textArea.textProperty().addListener(textListener);
        textArea.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (isFocused) {
                if (blockType != MarkdownBlockType.Paragraph) showMarkdown();
                textArea.textProperty().addListener(textListener);
            } else {
                textArea.textProperty().removeListener(textListener);
                if (blockType != MarkdownBlockType.Paragraph) hideMarkdown();
            }
        });
        overrideKeyPressEvent();
    }

    public static StyleClassedTextArea create(EditorController editorController) {
        return new MarkdownBlock(editorController).textArea;
    }

    private void showMarkdown() {
        if (blockType.renderedTextMatchesMarkdown) setMarkdownCodeStyle();
        else {
            String markdownCode = blockType.getMarkdownCode(textArea.getText());
            if (blockType.renderedText != null) textArea.deleteText(0, blockType.renderedText.length());
            textArea.insertText(0, markdownCode);
        }
    }

    private void hideMarkdown() {
        if (blockType.renderedTextMatchesMarkdown) removeMarkdownCodeStyle();
        else {
            String markdownCode = blockType.getMarkdownCode(textArea.getText());
            textArea.deleteText(0, markdownCode.length());
            if (blockType.renderedText != null) textArea.insertText(0, blockType.renderedText);
        }
    }

    public void setMarkdownCodeStyle() {
        String markdownCode = blockType.getMarkdownCode(textArea.getText());
        textArea.setStyleClass(0, markdownCode.length(), "md");
    }

    public void removeMarkdownCodeStyle() {
        String markdownCode = blockType.getMarkdownCode(textArea.getText());
        textArea.setStyleClass(0, markdownCode.length(), "");
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
            else if (event.getCode().equals(KeyCode.BACK_SPACE)) handleBackSpaceKeyPress(event);
            else if (event.getCode().equals(KeyCode.UP)) handleUpArrowKeyPress();
            else if (event.getCode().equals(KeyCode.DOWN)) handleDownArrowKeyPress();
        });
        textArea.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (event.getCharacter().equals("*")) handleAsteriskTyped(event);
        });
    }

    private void handleAsteriskTyped(KeyEvent event) {
        int caretPosition = textArea.getCaretPosition();

        List<String> classes;
        int previousAsterisksLength = getPreviousAsterisks(caretPosition).length();
        if (previousAsterisksLength == 0) classes = List.of("i");
        else if (previousAsterisksLength == 1) classes = List.of("b");
        else classes = Arrays.asList("b", "i");

        textArea.insertText(caretPosition, "**");
        previousAsterisksLength++;
        int start = caretPosition - previousAsterisksLength + 1;
        int end = caretPosition + previousAsterisksLength + 1;
        textArea.setStyle(start, end, classes);
        textArea.moveTo(caretPosition + 1);
        event.consume();
    }

    private String getPreviousAsterisks(int caretPosition) {
        String textBefore = textArea.getText(0, caretPosition);
        Matcher matcher = Pattern.compile("\\**$").matcher(textBefore);
        if (!matcher.find()) return "";
        return matcher.group();
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

    private void handleBackSpaceKeyPress(KeyEvent event) {
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
        } else if (event.isShortcutDown()) textArea.clear();
        else if (textArea.getSelectedText() != null) textArea.replaceSelection("");
        else textArea.deletePreviousChar();
    }

    private void handleUpArrowKeyPress() {
        int currentIndex = editorController.getBlockIndex(textArea);

        if (currentIndex != 0) {
            StyleClassedTextArea aboveCurrentCodeArea = editorController.getBlockAt(currentIndex - 1);
            aboveCurrentCodeArea.requestFocus();

        } // else when they try to move up when at block 1
    }

    private void handleDownArrowKeyPress() {
        int blocksLength = textArea.getLength();
        int currentIndex = editorController.getBlockIndex(textArea);

        if (currentIndex < blocksLength - 2) {
            StyleClassedTextArea belowCurrentCodeArea = editorController.getBlockAt(currentIndex + 1);
            belowCurrentCodeArea.requestFocus();
        }
    }
}

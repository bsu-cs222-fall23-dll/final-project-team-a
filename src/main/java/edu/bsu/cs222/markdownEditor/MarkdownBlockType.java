package edu.bsu.cs222.markdownEditor;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.regex.Pattern;

public enum MarkdownBlockType {
    Paragraph(null, "p"),
    Heading1("#", "h1"),
    List("-", "•", "ul");

    public final String regexp;
    private final String markdown, renderedText, className;

    MarkdownBlockType(String markdown, String className) {
        this(markdown, null, className);
    }

    MarkdownBlockType(String markdown, String renderedText, String className) {
        this.markdown = markdown;
        this.renderedText = renderedText;
        this.className = className;
        this.regexp = markdown == null ? null : "^" + Pattern.quote(markdown) + " .*";
    }

    public void setStyle(StyleClassedTextArea textArea) {
        textArea.getStyleClass().add(className);
        if (markdown != null)
            textArea.setStyleClass(0, markdown.length() + 1, "md");
    }

    public void removeStyle(StyleClassedTextArea textArea) {
        textArea.getStyleClass().remove(className);
    }

    public void removeMarkdown(CodeArea block) {
        if (markdown != null) block.deleteText(0, markdown.length() + 1);
        if (renderedText != null) block.insertText(0, renderedText + " ");
    }

    public void addMarkdown(CodeArea block) {
        if (renderedText != null) block.deleteText(0, renderedText.length() + 1);
        if (markdown != null) {
            block.insertText(0, markdown + " ");
            block.setStyleClass(0, markdown.length() + 1, "md");
        }
    }
}

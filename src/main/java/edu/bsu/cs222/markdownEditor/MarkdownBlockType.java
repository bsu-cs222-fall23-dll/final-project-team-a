package edu.bsu.cs222.markdownEditor;

import org.fxmisc.richtext.StyleClassedTextArea;

public enum MarkdownBlockType {
    Paragraph(null, "p"),
    Heading1("#", "h1"),
    Heading2("##", "h2"),
    Heading3("###", "h3"),
    UnorderedList(null, null),
    OrderedList(null, null);

    public final String regexp;
    private final String markdown, className;

    MarkdownBlockType(String markdown, String className) {
        this.markdown = markdown;
        this.className = className;
        this.regexp = markdown == null ? null : "^" + markdown + " .*";
    }

    public void setStyle(StyleClassedTextArea textArea) {
        textArea.getStyleClass().add(className);
        if (markdown != null)
            textArea.setStyleClass(0, markdown.length(), "md");
    }

    public void removeStyle(StyleClassedTextArea textArea) {
        textArea.getStyleClass().remove(className);
    }
}

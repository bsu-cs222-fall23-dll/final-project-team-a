package edu.bsu.cs222.markdownEditor;

import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MarkdownBlockType {
    Paragraph(null, "p"),
    Heading1("#", "h1"),
    Heading2("##", "h2"),
    Heading3("###", "h3"),
    UnorderedList("-", "•", "ul"),
    OrderedList(null, "\\d+\\.", "", "ol");

    public final String regexp, renderedText;
    public final boolean renderedTextMatchesMarkdown;
    private final String markdown, className;

    MarkdownBlockType(String markdown, String className) {
        this(markdown, markdown, null, className);
    }

    MarkdownBlockType(String markdown, String renderedText, String className) {
        this(markdown, markdown, renderedText + " ", className);
    }

    MarkdownBlockType(String markdown, String markdownRegexp, String renderedText, String className) {
        this.markdown = markdown == null ? null : markdown + " ";
        this.renderedTextMatchesMarkdown = renderedText != null && renderedText.isEmpty();
        this.renderedText = renderedTextMatchesMarkdown ? null : renderedText;
        this.className = className;
        this.regexp = markdownRegexp == null ? null : "^(" + markdownRegexp + " )(.*)";
    }

    public void setStyle(StyleClassedTextArea textArea) {
        textArea.getStyleClass().add(className);
        if (regexp != null) {
            String text = textArea.getText();
            int markdownLength = getMarkdownCode(text).length();
            textArea.setStyleClass(0, markdownLength, "md");
        }
    }

    public String getMarkdownCode(String text) {
        if (markdown != null) return markdown;
        Matcher matcher = Pattern.compile(regexp).matcher(text);
        if (!matcher.find()) throw new RuntimeException("No markdown found.");
        return matcher.group(1);
    }

    public void removeStyle(StyleClassedTextArea textArea) {
        textArea.getStyleClass().remove(className);
    }

    public int getMarkdownLength() {
        if (markdown == null) return 0;
        return markdown.length();
    }
}

package edu.bsu.cs222.markdownEditor.textarea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MarkdownBlockType {
    Paragraph(null, "p"),
    Heading1("#", "h1"),
    Heading2("##", "h2"),
    Heading3("###", "h3"),
    UnorderedList("-", "ul"),
    OrderedList("ol") {
        private final String regex = "^(\\d+\\. ).*";

        @Override
        public boolean matches(String text) {
            return text.matches(regex);
        }

        @Override
        public String getMarkdownSyntax(String text) {
            Matcher matcher = Pattern.compile(regex).matcher(text);
            if (!matcher.find()) throw new RuntimeException("Text doesn't match MarkdownBlockType");
            return matcher.group(1);
        }
    };

    public final String className;
    private final String typeSyntax;

    MarkdownBlockType(String className) {
        this(null, className);
    }

    MarkdownBlockType(String typeSyntax, String className) {
        this.typeSyntax = typeSyntax + " ";
        this.className = className;
    }

    public boolean matches(String text) {
        return text.startsWith(typeSyntax);
    }

    public String getMarkdownSyntax(String text) {
        return typeSyntax;
    }

    static public MarkdownBlockType findType(String text) {
        for (MarkdownBlockType blockType : values()) {
            if (blockType.equals(MarkdownBlockType.Paragraph)) continue;
            if (blockType.matches(text)) return blockType;
        }
        return Paragraph;
    }
}

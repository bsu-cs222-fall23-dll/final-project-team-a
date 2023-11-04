package edu.bsu.cs222.markdownEditor.textarea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ParagraphStyle {
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
            if (!matcher.find()) throw new RuntimeException("Text doesn't match ParagraphStyle");
            return matcher.group(1);
        }

        @Override
        public String createMarkdownSyntax() {
            return "1. ";
        }
    };

    public final String className;
    private final String typeSyntax;

    ParagraphStyle(String className) {
        this(null, className);
    }

    ParagraphStyle(String typeSyntax, String className) {
        this.typeSyntax = typeSyntax == null ? null : typeSyntax + " ";
        this.className = className;
    }

    public boolean matches(String text) {
        if (typeSyntax == null) return true;
        return text.startsWith(typeSyntax);
    }

    public String getMarkdownSyntax(String text) {
        return typeSyntax;
    }

    public String createMarkdownSyntax() {
        return typeSyntax == null ? "" : typeSyntax;
    }

    static public ParagraphStyle findType(String text) {
        for (ParagraphStyle style : values()) {
            if (style.equals(ParagraphStyle.Paragraph)) continue;
            if (style.matches(text)) return style;
        }
        return Paragraph;
    }
}

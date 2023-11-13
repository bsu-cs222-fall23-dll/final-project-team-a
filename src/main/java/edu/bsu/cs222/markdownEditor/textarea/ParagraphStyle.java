package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.ParagraphSyntaxType;

public enum ParagraphStyle {
    Paragraph("p"),
    Heading1("h1"),
    Heading2("h2"),
    Heading3("h3"),
    UnorderedList("ul"),
    OrderedList("ol");

    public final String className;

    ParagraphStyle(String className) {
        this.className = className;
    }

    public ParagraphSyntaxType getSyntaxType() {
        try {
            return ParagraphSyntaxType.valueOf(this.name());
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }
}

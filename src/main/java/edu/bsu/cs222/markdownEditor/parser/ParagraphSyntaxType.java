package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ParagraphSyntaxType {
    Heading1(ParagraphStyle.Heading1, "#"),
    Heading2(ParagraphStyle.Heading2, "##"),
    Heading3(ParagraphStyle.Heading3, "###");

    private final ParagraphStyle style;
    private final Pattern pattern;

    ParagraphSyntaxType(ParagraphStyle style, String syntax) {
        this.style = style;
        this.pattern = Pattern.compile("^" + syntax + " ", Pattern.MULTILINE);
    }

    void forEachReference(String text, Consumer<ParagraphSyntaxReference> action) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) action.accept(createReference(matcher));
    }

    protected ParagraphSyntaxReference createReference(Matcher match) {
        return new ParagraphSyntaxReference(match, this.style);
    }
}

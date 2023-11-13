package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ParagraphSyntaxType {
    Heading1(ParagraphStyle.Heading1, "^# "),
    Heading2(ParagraphStyle.Heading2, "^## "),
    Heading3(ParagraphStyle.Heading3, "^### "),
    UnorderedList(ParagraphStyle.UnorderedList, "^- ") {
        @Override
        protected ParagraphSyntaxReference createReference(Matcher match) {
            return new UnorderedListSyntaxReference(match);
        }
    },
    OrderedList(ParagraphStyle.OrderedList, "^(\\d+)\\. ") {
        @Override
        protected ParagraphSyntaxReference createReference(Matcher match) {
            return new OrderedListSyntaxReference(match);
        }
    };

    private final ParagraphStyle style;
    private final Pattern pattern;

    ParagraphSyntaxType(ParagraphStyle style, String regexp) {
        this.style = style;
        this.pattern = Pattern.compile(regexp, Pattern.MULTILINE);
    }

    void forEachReference(String text, Consumer<ParagraphSyntaxReference> action) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) action.accept(createReference(matcher));
    }

    protected ParagraphSyntaxReference createReference(Matcher match) {
        return new ParagraphSyntaxReference(match, this.style);
    }
}

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
    };
    //    OrderedList("ol") {
    //        private final String regex = "^(\\d+\\. ).*";
    //
    //        @Override
    //        public boolean matches(String text) {
    //            return text.matches(regex);
    //        }
    //
    //        @Override
    //        public String getMarkdownSyntax(String text) {
    //            Matcher matcher = Pattern.compile(regex).matcher(text);
    //            if (!matcher.find()) throw new RuntimeException("Text doesn't match ParagraphStyle");
    //            return matcher.group(1);
    //        }
    //
    //        @Override
    //        public String createMarkdownSyntax() {
    //            return "1. ";
    //        }
    //    };

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

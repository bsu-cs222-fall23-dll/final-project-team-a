package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ParagraphSyntaxType {
    Heading1("#"),
    Heading2("##"),
    Heading3("###"),
    UnorderedList("-") {
        @Override
        protected ParagraphSyntaxReference createReference(Matcher match) {
            return new UnorderedListSyntaxReference(match);
        }
    },
    OrderedList("1.", "^(\\d+)\\.") {
        @Override
        protected ParagraphSyntaxReference createReference(Matcher match) {
            return new OrderedListSyntaxReference(match);
        }
    };

    private final String defaultSyntax, regexp;
    private final Pattern pattern;

    ParagraphSyntaxType(String defaultSyntax) {
        this(defaultSyntax, "^" + defaultSyntax);
    }

    ParagraphSyntaxType(String defaultSyntax, String regexp) {
        this.defaultSyntax = defaultSyntax + " ";
        this.regexp = regexp + " ";
        this.pattern = Pattern.compile(this.regexp, Pattern.MULTILINE);
    }

    public boolean matches(String text) {
        return text.matches(regexp + ".+");
    }

    public int getSyntaxLength(String text) {
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) throw new RuntimeException("Text doesn't match regexp");
        return matcher.group().length();
    }

    ParagraphSyntaxReference getReference(String text) {
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) return null;
        return createReference(matcher);
    }

    public ParagraphSyntaxReference createReference() {
        return createReference(defaultSyntax);
    }

    public ParagraphSyntaxReference createReference(String syntax) {
        Matcher matcher = pattern.matcher(syntax);
        if (!matcher.find()) throw new RuntimeException("Text doesn't match regexp");
        return createReference(matcher);
    }

    public ParagraphStyle getStyle() {
        return ParagraphStyle.valueOf(this.name());
    }

    protected ParagraphSyntaxReference createReference(Matcher match) {
        return new ParagraphSyntaxReference(match, getStyle());
    }
}

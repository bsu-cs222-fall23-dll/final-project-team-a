package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum InlineSyntaxType {
    Link(LinkSyntaxReference.getTextStyle(),
            "(?<!\\\\)\\[(.+)(?<!\\\\)]\\((.+)(?<!\\\\)\\)") {
        @Override
        SyntaxReference createReference(Matcher match) {
            return new LinkSyntaxReference(match);
        }
    },
    ItalicAndBold(TextStyle.EMPTY.add(TextStyle.Property.Italics).add(TextStyle.Property.Bold),
            "(?<![*\\\\])([*]{3})(?![*])\\b(.+?)\\b(?<![*\\s\\\\])\\1",
            "(?<![_\\\\])(___)(?!_)\\b(.+?)\\b(?<![_\\s\\\\])\\1") {
        @Override
        SyntaxReference createReference(Matcher match) {
            return new TagWrappedSyntaxReference(this, match);
        }
    },
    Bold(TextStyle.EMPTY.add(TextStyle.Property.Bold),
            "(?<![*\\\\])([*]{2})(?![*])\\b(.+?)\\b(?<![*\\s\\\\])\\1",
            "(?<![_\\\\])(__)(?!_)\\b(.+?)\\b(?<![_\\s\\\\])\\1") {
        @Override
        SyntaxReference createReference(Matcher match) {
            return new TagWrappedSyntaxReference(this, match);
        }
    },
    Italic(TextStyle.EMPTY.add(TextStyle.Property.Italics),
            "(?<![*\\\\])([*])(?![*])\\b(.+?)\\b(?<![*\\s\\\\])\\1",
            "(?<![_\\\\])(_)(?!_)\\b(.+?)\\b(?<![_\\s\\\\])\\1") {
        @Override
        SyntaxReference createReference(Matcher match) {
            return new TagWrappedSyntaxReference(this, match);
        }
    },
    Code(TextStyle.EMPTY.add(TextStyle.Property.Code),
            "(?<![`\\\\])(`)\\b(.+?)\\b(?<![`\\\\])\\1") {
        @Override
        SyntaxReference createReference(Matcher match) {
            return new TagWrappedSyntaxReference(this, match);
        }
    },
    Strikethrough(TextStyle.STRIKETHROUGH, "(?<![`\\\\])(~~)\\b(.+?)\\b(?<![`\\\\])\\1") {
        @Override
        SyntaxReference createReference(Matcher match) {
            return new TagWrappedSyntaxReference(this, match);
        }
    },
    Highlight(TextStyle.HIGHLIGHT, "(?<![`\\\\])(==)\\b(.+?)\\b(?<![`\\\\])\\1") {
        @Override
        SyntaxReference createReference(Matcher match) {
            return new TagWrappedSyntaxReference(this, match);
        }
    };

    private final TextStyle textStyle;
    private final List<String> regexps;

    InlineSyntaxType(TextStyle textStyle, String... regexps) {
        this.textStyle = textStyle;
        this.regexps = List.of(regexps);
    }

    abstract SyntaxReference createReference(Matcher match);

    void forEachReference(StringBuilder stringBuilder, Consumer<SyntaxReference> action) {
        regexps.forEach(regexp -> {
            Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE);
            DynamicMatcher matcher = new DynamicMatcher(pattern, stringBuilder);
            matcher.forEachMatch(match -> action.accept(createReference(match)));
        });
    }

    TextStyle getTextStyle() {
        return textStyle;
    }
}

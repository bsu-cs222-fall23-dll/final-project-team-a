package edu.bsu.cs222.markdownEditor;

import java.util.ArrayList;
import java.util.List;

public class TextStyle {
    public static final TextStyle EMPTY = new TextStyle();

    private final Boolean italic, bold, code;

    private TextStyle() {
        this(false, false, false);
    }

    private TextStyle(Boolean italic,
                      Boolean bold,
                      Boolean code) {
        this.italic = italic;
        this.bold = bold;
        this.code = code;
    }

    public TextStyle updateWith(TextStyle mixin) {
        return new TextStyle(
                mixin.bold || bold,
                mixin.italic || italic,
                mixin.code || code
        );
    }

    public TextStyle updateBold(boolean bold) {
        return new TextStyle(bold, italic, code);
    }

    public TextStyle updateItalic(boolean italic) {
        return new TextStyle(bold, italic, code);
    }

    public TextStyle updateCode(boolean code) {
        return new TextStyle(bold, italic, code);
    }

    public List<String> toList() {
        List<String> classes = new ArrayList<>();
        if (bold) classes.add("b");
        if (italic) classes.add("i");
        if (code) classes.add("inline-code");
        return classes;
    }
}

package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.parser.syntax.*;

import java.util.ArrayList;
import java.util.List;

public class MarkdownParser {

    private final StringBuilder stringBuilder;
    private int start = 0;
    private boolean parseItalic = true, parseBold = true, parseCode = true, parseLink = true;

    public MarkdownParser(String text) {
        this.stringBuilder = new StringBuilder(text);
    }

    public MarkdownParser createChildParser(String text, int start) {
        MarkdownParser parser = new MarkdownParser(text);
        parser.start = start;
        parser.parseItalic = this.parseItalic;
        parser.parseBold = this.parseBold;
        parser.parseCode = this.parseCode;
        parser.parseLink = this.parseLink;
        return parser;
    }

    public List<SyntaxReference> getMarkdownSyntax() {
        List<SyntaxReference> references = new ArrayList<>();
        if (parseItalic) ItalicSyntaxReference.forEachReference(stringBuilder, reference -> {
            reference.offsetStart(start);
            references.add(reference);
            MarkdownParser childParser = createChildParser(reference.getText(), reference.getTextStartIndex());
            childParser.parseItalic = false;
            references.addAll(childParser.getMarkdownSyntax());
        });
        if (parseBold) BoldSyntaxReference.forEachReference(stringBuilder, reference -> {
            reference.offsetStart(start);
            references.add(reference);
            MarkdownParser childParser = createChildParser(reference.getText(), reference.getTextStartIndex());
            childParser.parseBold = false;
            references.addAll(childParser.getMarkdownSyntax());
        });
        if (parseItalic && parseBold) ItalicAndBoldSyntaxReference.forEachReference(stringBuilder, reference -> {
            reference.offsetStart(start);
            references.add(reference);
            MarkdownParser childParser = createChildParser(reference.getText(), reference.getTextStartIndex());
            childParser.parseItalic = false;
            childParser.parseBold = false;
            references.addAll(childParser.getMarkdownSyntax());
        });
        if (parseCode) CodeSyntaxReference.forEachReference(stringBuilder, reference -> {
            reference.offsetStart(start);
            references.add(reference);
            MarkdownParser childParser = createChildParser(reference.getText(), reference.getTextStartIndex());
            childParser.parseCode = false;
            references.addAll(childParser.getMarkdownSyntax());
        });
        if (parseLink) LinkSyntaxReference.forEachReference(stringBuilder, reference -> {
            reference.offsetStart(start);
            references.add(reference);
            MarkdownParser childParser = createChildParser(reference.text, reference.getTextStartIndex());
            childParser.parseLink = false;
            references.addAll(childParser.getMarkdownSyntax());
        });
        return references;
    }
}

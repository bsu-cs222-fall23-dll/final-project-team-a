package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.parser.syntax.*;

import java.util.ArrayList;
import java.util.List;

public class MarkdownParser {
    private final String text;

    public MarkdownParser(String text) {
        this.text = text;
    }

    public List<SyntaxReference> getMarkdownSyntax() {
        List<SyntaxReference> references = new ArrayList<>();
        references.addAll(ItalicSyntaxReference.findReferences(text));
        references.addAll(BoldSyntaxReference.findReferences(text));
        references.addAll(ItalicAndBoldSyntaxReference.findReferences(text));
        references.addAll(CodeSyntaxReference.findReferences(text));
        return references;
    }
}

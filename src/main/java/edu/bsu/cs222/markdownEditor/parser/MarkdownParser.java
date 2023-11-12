package edu.bsu.cs222.markdownEditor.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarkdownParser {

    private final StringBuilder stringBuilder;
    private int start = 0;
    private Set<InlineSyntaxType> parentSyntaxTypes = new HashSet<>();

    public MarkdownParser(String text) {
        this.stringBuilder = new StringBuilder(text);
    }

    public MarkdownParser createChildParser(InlineSyntaxType type, SyntaxReference reference) {
        MarkdownParser parser = new MarkdownParser(reference.getText());
        parser.start = reference.getTextStart();
        parser.parentSyntaxTypes = new HashSet<>(this.parentSyntaxTypes);
        parser.parentSyntaxTypes.add(type);
        return parser;
    }

    public List<SyntaxReference> getMarkdownSyntax() {
        List<SyntaxReference> references = new ArrayList<>();
        findReferences(references);
        return references;
    }

    private void findReferences(List<SyntaxReference> references) {
        for (InlineSyntaxType inlineSyntaxType : InlineSyntaxType.values()) {
            if (isChildOfType(inlineSyntaxType)) continue;
            inlineSyntaxType.forEachReference(stringBuilder, reference -> {
                reference.offsetStart(start);
                references.add(reference);
                MarkdownParser childParser = createChildParser(inlineSyntaxType, reference);
                childParser.findReferences(references);
            });
        }
    }

    public boolean isChildOfType(InlineSyntaxType type) {
        return parentSyntaxTypes.contains(type);
    }
}

package edu.bsu.cs222.markdownEditor.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarkdownParser {

    private final String text;
    private final StringBuilder stringBuilder;
    private int start = 0;
    private Set<InlineSyntaxType> parentSyntaxTypes = new HashSet<>();

    public MarkdownParser(String text) {
        this.text = text;
        this.stringBuilder = new StringBuilder(text);
    }

    public List<SyntaxReference> getReferences() {
        List<SyntaxReference> references = new ArrayList<>(getParagraphReferences());
        findInlineReferences(references);
        return references;
    }

    private List<ParagraphSyntaxReference> getParagraphReferences() {
        List<ParagraphSyntaxReference> references = new ArrayList<>();
        for (ParagraphSyntaxType paragraphSyntaxType : ParagraphSyntaxType.values()) {
            paragraphSyntaxType.forEachReference(text, references::add);
        }
        return references;
    }

    private MarkdownParser createChildParser(InlineSyntaxType type, SyntaxReference reference) {
        MarkdownParser parser = new MarkdownParser(reference.getText());
        parser.start = reference.getTextStart();
        parser.parentSyntaxTypes = new HashSet<>(this.parentSyntaxTypes);
        parser.parentSyntaxTypes.add(type);
        return parser;
    }

    private void findInlineReferences(List<SyntaxReference> references) {
        for (InlineSyntaxType inlineSyntaxType : InlineSyntaxType.values()) {
            if (isChildOfType(inlineSyntaxType)) continue;
            inlineSyntaxType.forEachReference(stringBuilder, reference -> {
                reference.offsetStart(start);
                references.add(reference);
                if (reference.hasStylableText()) {
                    MarkdownParser childParser = createChildParser(inlineSyntaxType, reference);
                    childParser.findInlineReferences(references);
                }
            });
        }
    }

    private boolean isChildOfType(InlineSyntaxType type) {
        return parentSyntaxTypes.contains(type);
    }
}

package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LineParser {

    private final String text;
    private int start = 0;
    private Set<InlineSyntaxType> parentSyntaxTypes = new HashSet<>();
    private ParagraphStyle paragraphStyle = ParagraphStyle.Paragraph;
    private final List<SyntaxReference> syntaxReferences = new ArrayList<>();

    public LineParser(String text) {
        this.text = text;
        getParagraphReference(text);
        getInlineReferences(syntaxReferences);
    }

    public ParagraphStyle getParagraphStyle() {
        return paragraphStyle;
    }

    public List<SyntaxReference> getSyntaxReferences() {
        return syntaxReferences;
    }

    private void getParagraphReference(String text) {
        for (ParagraphSyntaxType paragraphSyntaxType : ParagraphSyntaxType.values()) {
            ParagraphSyntaxReference reference = paragraphSyntaxType.getReference(text);
            if (reference != null) {
                paragraphStyle = reference.getParagraphStyle();
                syntaxReferences.add(reference);
            }
        }
    }

    private void getInlineReferences(List<SyntaxReference> references) {
        StringBuilder stringBuilder = new StringBuilder(text);
        for (InlineSyntaxType inlineSyntaxType : InlineSyntaxType.values()) {
            if (isChildOfSyntax(inlineSyntaxType)) continue;
            inlineSyntaxType.forEachReference(stringBuilder, reference -> {
                reference.offsetStart(start);
                references.add(reference);
                if (reference.hasStylableText()) {
                    LineParser childParser = createChildParser(inlineSyntaxType, reference);
                    childParser.getInlineReferences(references);
                }
            });
        }
    }

    private LineParser createChildParser(InlineSyntaxType type, SyntaxReference reference) {
        LineParser parser = new LineParser(reference.getText());
        parser.start = reference.getTextStart();
        parser.parentSyntaxTypes = new HashSet<>(this.parentSyntaxTypes);
        parser.parentSyntaxTypes.add(type);
        return parser;
    }

    private boolean isChildOfSyntax(InlineSyntaxType type) {
        return parentSyntaxTypes.contains(type);
    }

}

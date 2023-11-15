package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;

import java.util.ArrayList;
import java.util.List;

public class RestructuredMarkdownParser {

    private List<List<SyntaxReference>> syntaxReferences = new ArrayList<>();
    private List<ParagraphStyle> paragraphStyles = new ArrayList<>();

    RestructuredMarkdownParser(String text) {
        for (String line : text.split("/n")) {
            LineParser lineParser = new LineParser(line);
            paragraphStyles.add(lineParser.getParagraphStyle());
            syntaxReferences.add(lineParser.getSyntaxReferences());

        }
    }

    public List<List<SyntaxReference>> getSyntaxReferences() {
        return syntaxReferences;
    }

    public List<ParagraphStyle> getParagraphStyles() {
        return paragraphStyles;
    }
}

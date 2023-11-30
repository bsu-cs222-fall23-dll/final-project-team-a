package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnorderedListSyntaxReferenceTest {

    private final ParagraphSyntaxReference syntaxReference;

    UnorderedListSyntaxReferenceTest() {
        String markdown = "- Hello cruel world!";
        LineParser parser = new LineParser(markdown);
        List<SyntaxReference> syntaxReferences = parser.getSyntaxReferences();
        if (syntaxReferences.get(0) instanceof ParagraphSyntaxReference paragraphSyntaxReference) {
            syntaxReference = paragraphSyntaxReference;
        } else {
            throw new AssertionError("SyntaxReference is the wrong type");
        }
    }

    @Test
    public void typeTest() {
        assertEquals(ParagraphStyle.UnorderedList, syntaxReference.paragraphStyle);
    }

    @Test
    public void testTag() {
        assertEquals("- ", syntaxReference.syntax);
    }

}

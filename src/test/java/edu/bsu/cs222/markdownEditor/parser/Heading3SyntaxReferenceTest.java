package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Heading3SyntaxReferenceTest {

    private final ParagraphSyntaxReference syntaxReference;

    Heading3SyntaxReferenceTest() {
        String markdown = "### Hello cruel world!";
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
        assertEquals(ParagraphStyle.Heading3, syntaxReference.paragraphStyle);
    }

    @Test
    public void testTag() {
        assertEquals("### ", syntaxReference.syntax);
    }

}

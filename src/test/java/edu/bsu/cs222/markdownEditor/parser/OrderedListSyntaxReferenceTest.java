package edu.bsu.cs222.markdownEditor.parser;

import edu.bsu.cs222.markdownEditor.textarea.ParagraphStyle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderedListSyntaxReferenceTest {

    private final ParagraphSyntaxReference syntaxReference;

    OrderedListSyntaxReferenceTest() {
        String markdown = "1. Hello cruel world!";
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
        assertEquals(ParagraphStyle.OrderedList, syntaxReference.paragraphStyle);
    }

    @Test
    public void testTag() {
        assertEquals("1. ", syntaxReference.syntax);
    }

}

package edu.bsu.cs222.markdownEditor.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoldSyntaxReferenceTest {

    private final TagWrappedSyntaxReference syntaxReference;

    BoldSyntaxReferenceTest() {
        String markdown = "Hello **cruel** world!";
        LineParser parser = new LineParser(markdown);
        List<SyntaxReference> syntaxReferences = parser.getSyntaxReferences();
        if (syntaxReferences.get(0) instanceof TagWrappedSyntaxReference tagWrappedSyntaxReference) {
            syntaxReference = tagWrappedSyntaxReference;
        } else {
            throw new AssertionError("SyntaxReference is the wrong type");
        }
    }

    @Test
    public void typeTest() {
        assertEquals(InlineSyntaxType.Bold, syntaxReference.type);
    }

    @Test
    public void testTag() {
        assertEquals("**", syntaxReference.tag);
    }

    @Test
    public void testText() {
        assertEquals("cruel", syntaxReference.getText());
    }

    @Test
    public void testStartPosition() {
        assertEquals(6, syntaxReference.start);
    }
}


package edu.bsu.cs222.markdownEditor.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkSyntaxReferenceTest {

    private final TagWrappedSyntaxReference syntaxReference;

    LinkSyntaxReferenceTest() {
        String markdown = "Hello [google](https://www.google.com) world!";
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
        assertEquals(InlineSyntaxType.Link, syntaxReference.type);
    }

    @Test
    public void testTag() {
        assertEquals("[]()", syntaxReference.tag);
    }

    @Test
    public void testText() {
        assertEquals("google", syntaxReference.getText());
    }

    @Test
    public void testStartPosition() {
        assertEquals(6, syntaxReference.start);
    }
}


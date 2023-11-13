package edu.bsu.cs222.markdownEditor.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParagraphSyntaxTypeTest {
    private final String RegexpErrorMessage = "this regular expression doesn't match the text;";

    @Test
    public void heading1Regexp() {
        String text = "# Hello World";
        assertTrue(ParagraphSyntaxType.Heading1.matches(text), RegexpErrorMessage);
    }

    @Test
    public void heading2Regexp() {
        String text = "## Hello World";
        assertTrue(ParagraphSyntaxType.Heading2.matches(text), RegexpErrorMessage);
    }

    @Test
    public void heading3Regexp() {
        String text = "### Hello World";
        assertTrue(ParagraphSyntaxType.Heading3.matches(text), RegexpErrorMessage);
    }

    @Test
    public void unorderedListRegexp() {
        String text = "- Hello World";
        assertTrue(ParagraphSyntaxType.UnorderedList.matches(text), RegexpErrorMessage);
    }

    @Test
    public void orderedListRegexp() {
        String text = "1. Hello World";
        assertTrue(ParagraphSyntaxType.OrderedList.matches(text), RegexpErrorMessage);
    }

}
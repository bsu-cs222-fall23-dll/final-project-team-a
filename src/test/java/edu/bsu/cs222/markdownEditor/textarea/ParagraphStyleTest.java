package edu.bsu.cs222.markdownEditor.textarea;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParagraphStyleTest {
    private final String RegexpErrorMessage = "this regular expression doesn't match the text;";

    @Test
    public void heading1Regexp() {
        String text = "# Hello World";
        assertTrue(ParagraphStyle.Heading1.matches(text), RegexpErrorMessage);
    }

    @Test
    public void heading2Regexp() {
        String text = "## Hello World";
        assertTrue(ParagraphStyle.Heading2.matches(text), RegexpErrorMessage);
    }

    @Test
    public void heading3Regexp() {
        String text = "### Hello World";
        assertTrue(ParagraphStyle.Heading3.matches(text), RegexpErrorMessage);
    }

    @Test
    public void unorderedListRegexp() {
        String text = "- Hello World";
        assertTrue(ParagraphStyle.UnorderedList.matches(text), RegexpErrorMessage);
    }

    @Test
    public void orderedListRegexp() {
        String text = "1. Hello World";
        assertTrue(ParagraphStyle.OrderedList.matches(text), RegexpErrorMessage);
    }

}
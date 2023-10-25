package edu.bsu.cs222.markdownEditor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarkdownBlockTypeTest {
    private final String RegexpErrorMessage = "this regular expression doesn't match the text;";

    @Test
    public void paragraphRegexp() {
        assertNull(MarkdownBlockType.Paragraph.regexp);
    }

    @Test
    public void heading1Regexp() {
        String text = "# Hello World";
        assertTrue(text.matches(MarkdownBlockType.Heading1.regexp), RegexpErrorMessage);
    }

    @Test
    public void heading2Regexp() {
        String text = "## Hello World";
        assertTrue(text.matches(MarkdownBlockType.Heading2.regexp), RegexpErrorMessage);
    }

    @Test
    public void heading3Regexp() {
        String text = "### Hello World";
        assertTrue(text.matches(MarkdownBlockType.Heading3.regexp), RegexpErrorMessage);
    }

    @Test
    public void unorderedListRegexp() {
        String text = "- Hello World";
        assertTrue(text.matches(MarkdownBlockType.UnorderedList.regexp), RegexpErrorMessage);
    }

    @Test
    public void orderedListRegexp() {
        String text = "1. Hello World";
        assertTrue(text.matches(MarkdownBlockType.OrderedList.regexp), RegexpErrorMessage);
    }

}
package edu.bsu.cs222.markdownEditor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarkdownBlockTypeTest {
    private final String RegexpErrorMessage = "this regular expression doesn't match the text;";

    @Test
    public void heading1Regexp() {
        String text = "# Hello World";
        assertTrue(MarkdownBlockType.Heading1.matches(text), RegexpErrorMessage);
    }

    @Test
    public void heading2Regexp() {
        String text = "## Hello World";
        assertTrue(MarkdownBlockType.Heading2.matches(text), RegexpErrorMessage);
    }

    @Test
    public void heading3Regexp() {
        String text = "### Hello World";
        assertTrue(MarkdownBlockType.Heading3.matches(text), RegexpErrorMessage);
    }

    @Test
    public void unorderedListRegexp() {
        String text = "- Hello World";
        assertTrue(MarkdownBlockType.UnorderedList.matches(text), RegexpErrorMessage);
    }

    @Test
    public void orderedListRegexp() {
        String text = "1. Hello World";
        assertTrue(MarkdownBlockType.OrderedList.matches(text), RegexpErrorMessage);
    }

}
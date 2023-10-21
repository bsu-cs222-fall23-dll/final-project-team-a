package edu.bsu.cs222.markdownEditor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownBlockTypeTest {

    @Test
    public void paragraphRegexp() {
        assertNull(MarkdownBlockType.Paragraph.regexp);
    }

    @Test
    public void heading1Regexp() {
        String text = "# Hello World";
        assertTrue(text.matches(MarkdownBlockType.Heading1.regexp), "This regular expression doesn't match the text;");
    }

}
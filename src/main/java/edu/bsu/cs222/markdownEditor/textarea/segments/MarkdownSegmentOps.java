package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.fxmisc.richtext.model.TextOps;

import java.util.Optional;

public class MarkdownSegmentOps<Style> implements TextOps<MarkdownSegment, Style> {

    @Override
    public MarkdownSegment create(String text) {
        return new MarkdownSegment(text);
    }

    @Override
    public int length(MarkdownSegment markdown) {
        return markdown.length();
    }

    @Override
    public char charAt(MarkdownSegment markdown, int index) {
        return markdown.charAt(index);
    }

    @Override
    public String getText(MarkdownSegment markdown) {
        return markdown.getText();
    }

    @Override
    public MarkdownSegment subSequence(MarkdownSegment markdown, int start, int end) {
        return markdown.subSequence(start, end);
    }

    @Override
    public MarkdownSegment subSequence(MarkdownSegment markdown, int start) {
        return markdown.subSequence(start, markdown.length());
    }

    @Override
    public Optional<MarkdownSegment> joinSeg(MarkdownSegment currentSeg, MarkdownSegment nextSeg) {
        if (currentSeg.isPlainText() && nextSeg.isPlainText()) {
            return Optional.of(new MarkdownSegment(currentSeg.getText() + nextSeg.getText()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public MarkdownSegment createEmptySeg() {
        return new MarkdownSegment();
    }
}

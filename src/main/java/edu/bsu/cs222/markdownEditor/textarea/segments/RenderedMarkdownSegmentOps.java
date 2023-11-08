package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.fxmisc.richtext.model.TextOps;

import java.util.Optional;

public class RenderedMarkdownSegmentOps<Style> implements TextOps<RenderedMarkdownSegment, Style> {

    @Override
    public RenderedMarkdownSegment create(String text) {
        return new RenderedMarkdownSegment(text);
    }

    @Override
    public int length(RenderedMarkdownSegment segment) {
        return segment.length();
    }

    @Override
    public char charAt(RenderedMarkdownSegment segment, int index) {
        return segment.charAt(index);
    }

    @Override
    public String getText(RenderedMarkdownSegment segment) {
        return segment.getText();
    }

    @Override
    public RenderedMarkdownSegment subSequence(RenderedMarkdownSegment segment, int start, int end) {
        return segment.subSequence(start, end);
    }

    @Override
    public RenderedMarkdownSegment subSequence(RenderedMarkdownSegment segment, int start) {
        return segment.subSequence(start, segment.length());
    }

    @Override
    public Optional<RenderedMarkdownSegment> joinSeg(RenderedMarkdownSegment currentSeg,
                                                     RenderedMarkdownSegment nextSeg) {
        if (currentSeg.isPlainText() && nextSeg.isPlainText()) {
            return Optional.of(new RenderedMarkdownSegment(currentSeg.getText() + nextSeg.getText()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public RenderedMarkdownSegment createEmptySeg() {
        return new RenderedMarkdownSegment();
    }
}

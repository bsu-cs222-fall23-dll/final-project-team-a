package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.fxmisc.richtext.model.TextOps;

import java.util.Optional;

public class HiddenMarkdownSegmentOps<Style> implements TextOps<HiddenMarkdownSegment, Style> {

    @Override
    public HiddenMarkdownSegment create(String text) {
        return new HiddenMarkdownSegment(text);
    }

    @Override
    public int length(HiddenMarkdownSegment segment) {
        return segment.length();
    }

    @Override
    public char charAt(HiddenMarkdownSegment segment, int index) {
        return segment.charAt(index);
    }

    @Override
    public String getText(HiddenMarkdownSegment segment) {
        return segment.getText();
    }

    @Override
    public HiddenMarkdownSegment subSequence(HiddenMarkdownSegment segment, int start, int end) {
        return segment.subSequence(start, end);
    }

    @Override
    public HiddenMarkdownSegment subSequence(HiddenMarkdownSegment segment, int start) {
        return segment.subSequence(start, segment.length());
    }

    @Override
    public Optional<HiddenMarkdownSegment> joinSeg(HiddenMarkdownSegment currentSeg, HiddenMarkdownSegment nextSeg) {
        if (currentSeg.isPlainText() && nextSeg.isPlainText()) {
            return Optional.of(new HiddenMarkdownSegment(currentSeg.getText() + nextSeg.getText()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public HiddenMarkdownSegment createEmptySeg() {
        return new HiddenMarkdownSegment();
    }
}

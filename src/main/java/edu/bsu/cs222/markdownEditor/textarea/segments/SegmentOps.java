package edu.bsu.cs222.markdownEditor.textarea.segments;

import org.fxmisc.richtext.model.TextOps;

import java.util.Optional;

public class SegmentOps<Style> implements TextOps<Segment, Style> {

    @Override
    public Segment create(String text) {
        return new TextSegment(text);
    }

    @Override
    public int length(Segment segment) {
        return segment.length();
    }

    @Override
    public char charAt(Segment segment, int index) {
        return segment.charAt(index);
    }

    @Override
    public String getText(Segment segment) {
        return segment.getText();
    }

    @Override
    public Segment subSequence(Segment segment, int start, int end) {
        return segment.subSequence(start, end);
    }

    @Override
    public Segment subSequence(Segment segment, int start) {
        return segment.subSequence(start, segment.length());
    }

    @Override
    public Optional<Segment> joinSeg(Segment currentSeg, Segment nextSeg) {
        if (currentSeg.getClass().equals(nextSeg.getClass())) {
            return Optional.of(currentSeg.create(currentSeg.getText() + nextSeg.getText()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Segment createEmptySeg() {
        return new EmptySegment();
    }
}

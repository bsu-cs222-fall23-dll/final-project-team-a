package edu.bsu.cs222.markdownEditor.segments;

import org.fxmisc.richtext.model.TextOps;

import java.util.Optional;

public class TextSegmentOps<Style> implements TextOps<TextSegment, Style> {

    @Override
    public TextSegment create(String text) {
        return new TextSegment(text);
    }

    @Override
    public int length(TextSegment textSegment) {
        return textSegment.length();
    }

    @Override
    public char charAt(TextSegment textSegment, int index) {
        return textSegment.charAt(index);
    }

    @Override
    public String getText(TextSegment textSegment) {
        return textSegment.getText();
    }

    @Override
    public TextSegment subSequence(TextSegment textSegment, int start, int end) {
        return textSegment.subSequence(start, end);
    }

    @Override
    public TextSegment subSequence(TextSegment textSegment, int start) {
        return textSegment.subSequence(start, textSegment.length());
    }

    @Override
    public Optional<TextSegment> joinSeg(TextSegment currentSeg, TextSegment nextSeg) {
        return Optional.of(new TextSegment(currentSeg.getText() + nextSeg.getText()));
    }

    @Override
    public TextSegment createEmptySeg() {
        return new TextSegment();
    }
}

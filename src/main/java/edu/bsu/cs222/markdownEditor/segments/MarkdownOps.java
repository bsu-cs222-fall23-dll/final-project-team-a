package edu.bsu.cs222.markdownEditor.segments;

import org.fxmisc.richtext.model.TextOps;

import java.util.Optional;

public class MarkdownOps<Style> implements TextOps<Markdown, Style> {

    @Override
    public Markdown create(String text) {
        return new Markdown(text);
    }

    @Override
    public int length(Markdown markdown) {
        return markdown.length();
    }

    @Override
    public char charAt(Markdown markdown, int index) {
        return markdown.charAt(index);
    }

    @Override
    public String getText(Markdown markdown) {
        return markdown.getText();
    }

    @Override
    public Markdown subSequence(Markdown markdown, int start, int end) {
        return markdown.subSequence(start, end);
    }

    @Override
    public Markdown subSequence(Markdown markdown, int start) {
        return markdown.subSequence(start, markdown.length());
    }

    @Override
    public Optional<Markdown> joinSeg(Markdown currentSeg, Markdown nextSeg) {
        if (currentSeg.isPlainText() && nextSeg.isPlainText()) {
            return Optional.of(new Markdown(currentSeg.getText() + nextSeg.getText()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Markdown createEmptySeg() {
        return new Markdown();
    }
}

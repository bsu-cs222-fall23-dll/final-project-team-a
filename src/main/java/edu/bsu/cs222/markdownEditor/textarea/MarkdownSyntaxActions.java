package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.LineParser;
import edu.bsu.cs222.markdownEditor.parser.SyntaxReference;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentOps;
import org.fxmisc.richtext.MultiChangeBuilder;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.TextOps;

import java.util.List;
import java.util.function.Function;

public interface MarkdownSyntaxActions extends TextStyleActions {

    TextOps<Segment, TextStyle> SEGMENT_OPS = new SegmentOps<>();

    default void showParagraphRender(int paragraphIndex) {
        String text = getText(paragraphIndex);
        LineParser parser = new LineParser(text);
        setParagraphStyle(paragraphIndex, parser.getParagraphStyle());
        List<SyntaxReference> references = parser.getSyntaxReferences();
        replaceWithSyntaxReferences(paragraphIndex, references, SyntaxReference::getRenderedSegments);
        styleSyntaxReferences(paragraphIndex, references, SyntaxReference::getRenderedStyleSpans);
    }

    default void showParagraphMarkdown(int paragraphIndex) {
        String text = getText(paragraphIndex);
        LineParser parser = new LineParser(text);
        setParagraphStyle(paragraphIndex, parser.getParagraphStyle());
        List<SyntaxReference> references = parser.getSyntaxReferences();
        replaceWithSyntaxReferences(paragraphIndex, references, SyntaxReference::getMarkdownSegments);
        styleSyntaxReferences(paragraphIndex, references, SyntaxReference::getMarkdownStyleSpans);
    }

    private void replaceWithSyntaxReferences(int paragraphIndex,
            List<SyntaxReference> references,
            Function<SyntaxReference, SegmentList> getSegments) {
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        references.forEach(reference ->
                getSegments.apply(reference).forEach((start, segment) -> {
                    start += paragraphPosition;
                    int end = start + segment.length();
                    multiChangeBuilder.replace(start,
                            end,
                            ReadOnlyStyledDocument.fromSegment(segment,
                                    getParagraphStyleForInsertionAt(paragraphPosition),
                                    getStyleOfChar(start),
                                    SEGMENT_OPS));
                })
        );
        if (multiChangeBuilder.hasChanges()) multiChangeBuilder.commit();
    }

    private void styleSyntaxReferences(int paragraphIndex,
            List<SyntaxReference> references,
            Function<SyntaxReference, StyleSpans<TextStyle>> getStyleSpans) {
        clearStyle(paragraphIndex);
        references.forEach(reference -> {
            StyleSpans<TextStyle> styleSpans = getStyleSpans.apply(reference);
            overlayStyleSpans(paragraphIndex, reference.start, styleSpans);
        });
    }

    private int getParagraphPosition(int paragraphIndex) {
        int position = 0;
        for (int i = 0; i < paragraphIndex; i++) {
            position += getParagraph(i).length();
            position++; // for line break
        }
        return position;
    }
}
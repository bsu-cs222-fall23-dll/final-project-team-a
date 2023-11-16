package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.LineParser;
import edu.bsu.cs222.markdownEditor.parser.ParagraphSyntaxReference;
import edu.bsu.cs222.markdownEditor.parser.SyntaxReference;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentOps;
import org.fxmisc.richtext.MultiChangeBuilder;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.TextOps;

import java.util.List;
import java.util.function.Function;

public interface MarkdownSyntaxActions extends TextStyleActions {

    TextOps<Segment, TextStyle> SEGMENT_OPS = new SegmentOps<>();

    default void showParagraphRender(int paragraphIndex) {
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        String text = getText(paragraphIndex);
        LineParser parser = new LineParser(text);
        replaceWithSyntaxReferences(paragraphPosition,
                parser.getSyntaxReferences(),
                SyntaxReference::getRenderedSegments);
    }

    default void showParagraphMarkdown(int paragraphIndex) {
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        String text = getText(paragraphIndex);
        LineParser parser = new LineParser(text);
        replaceWithSyntaxReferences(paragraphPosition,
                parser.getSyntaxReferences(),
                SyntaxReference::getMarkdownSegments);
    }

    private void replaceWithSyntaxReferences(int paragraphPosition,
            List<SyntaxReference> references,
            Function<SyntaxReference, SegmentList> getSegments) {
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

    private void styleMarkdownParagraph(int currentParagraph, List<SyntaxReference> references) {
        clearStyle(currentParagraph);
        boolean isParagraph = true;
        for (SyntaxReference reference : references) {
            if (reference instanceof ParagraphSyntaxReference paragraphSyntaxReference) {
                styleParagraphMarkdownSyntax(currentParagraph, paragraphSyntaxReference);
                isParagraph = false;
            } else {
                styleInlineMarkdownSyntax(currentParagraph, reference);
            }
        }
        if (isParagraph) setParagraphStyle(currentParagraph, ParagraphStyle.Paragraph);
    }

    private void styleParagraphMarkdownSyntax(int currentParagraph, ParagraphSyntaxReference reference) {
        setParagraphStyle(currentParagraph, reference.getParagraphStyle());
        styleInlineMarkdownSyntax(currentParagraph, reference);
    }

    private void styleInlineMarkdownSyntax(int currentParagraph, SyntaxReference reference) {
        overlayStyleSpans(currentParagraph, reference.start, reference.getMarkdownStyleSpans());
    }

    private void styleRenderedParagraph(int currentParagraph, List<SyntaxReference> references) {
        clearStyle(currentParagraph);
        boolean isParagraph = true;
        for (SyntaxReference reference : references) {
            if (reference instanceof ParagraphSyntaxReference paragraphSyntaxReference) {
                styleParagraphRenderedSyntax(currentParagraph, paragraphSyntaxReference);
                isParagraph = false;
            } else {
                styleInlineRenderedSyntax(currentParagraph, reference);
            }
        }
        if (isParagraph) setParagraphStyle(currentParagraph, ParagraphStyle.Paragraph);
    }

    private void styleParagraphRenderedSyntax(int currentParagraph, ParagraphSyntaxReference reference) {
        setParagraphStyle(currentParagraph, reference.getParagraphStyle());
        styleInlineRenderedSyntax(currentParagraph, reference);
    }

    private void styleInlineRenderedSyntax(int currentParagraph, SyntaxReference reference) {
        reference.getRenderedStyleSpans().ifPresent(styleSpans ->
                overlayStyleSpans(currentParagraph, reference.start, styleSpans)
        );
    }

    /**
     * Edge-Case Bug: The last paragraph doesn't exisiting when empty.
     */
    private boolean lastParagraphEmpty(int paragraphIndex) {
        return paragraphIndex >= getParagraphs().size();
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
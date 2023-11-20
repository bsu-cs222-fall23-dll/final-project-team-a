package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.LineParser;
import edu.bsu.cs222.markdownEditor.parser.ParagraphSyntaxReference;
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
        replaceWithSyntaxReferences(paragraphIndex,
                parser.getSyntaxReferences(),
                SyntaxReference::getRenderedSegments,
                SyntaxReference::getRenderedStyleSpans);
    }

    default void showParagraphMarkdown(int paragraphIndex) {
        String text = getText(paragraphIndex);
        LineParser parser = new LineParser(text);
        replaceWithSyntaxReferences(paragraphIndex,
                parser.getSyntaxReferences(),
                SyntaxReference::getMarkdownSegments,
                SyntaxReference::getMarkdownStyleSpans);
    }

    private void replaceWithSyntaxReferences(int paragraphIndex,
            List<SyntaxReference> references,
            Function<SyntaxReference, SegmentList> getSegments,
            Function<SyntaxReference, StyleSpans<TextStyle>> getStyleSpans) {
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        references.forEach(reference -> {
            getSegments.apply(reference).forEach((start, segment) -> {
                start += paragraphPosition;
                int end = start + segment.length();
                multiChangeBuilder.replace(start,
                        end,
                        ReadOnlyStyledDocument.fromSegment(segment,
                                getParagraphStyleForInsertionAt(paragraphPosition),
                                getStyleOfChar(start),
                                SEGMENT_OPS));
            });
            StyleSpans<TextStyle> styleSpans = getStyleSpans.apply(reference);
            System.out.println(styleSpans);
            overlayStyleSpans(paragraphIndex, reference.start, styleSpans);
        });
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
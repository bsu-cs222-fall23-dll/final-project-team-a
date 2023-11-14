package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.MarkdownParser;
import edu.bsu.cs222.markdownEditor.parser.ParagraphSyntaxReference;
import edu.bsu.cs222.markdownEditor.parser.SyntaxReference;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentOps;
import org.fxmisc.richtext.MultiChangeBuilder;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.TextOps;

import java.util.List;

public interface MarkdownSyntaxActions extends TextStyleActions {

    TextOps<Segment, TextStyle> SEGMENT_OPS = new SegmentOps<>();

    default void hideParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        String text = getText(paragraphIndex);
        ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(paragraphIndex);
        new MarkdownParser(text).getReferences().forEach(syntaxReference -> {
            SegmentList segmentList = syntaxReference.getRenderedSegments();
            segmentList.forEach((start, segment) -> {
                start += paragraphPosition;
                int end = start + segment.length();
                multiChangeBuilder.replace(start,
                        end,
                        ReadOnlyStyledDocument.fromSegment(segment,
                                paragraphStyle,
                                getStyleOfChar(start),
                                SEGMENT_OPS));
            });
        });
        if (multiChangeBuilder.hasChanges()) multiChangeBuilder.commit();
    }

    default void showParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        String text = getText(paragraphIndex);
        MarkdownParser parser = new MarkdownParser(text);
        ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(paragraphIndex);
        List<SyntaxReference> references = parser.getReferences();
        references.forEach(syntaxReference -> {
            SegmentList segmentList = syntaxReference.getMarkdownSegments();
            segmentList.forEach((start, segment) -> {
                start += paragraphPosition;
                int end = start + segment.length();
                multiChangeBuilder.replace(start,
                        end,
                        ReadOnlyStyledDocument.fromSegment(segment,
                                paragraphStyle,
                                getStyleOfChar(start),
                                SEGMENT_OPS));
            });
        });
        if (multiChangeBuilder.hasChanges()) multiChangeBuilder.commit();
        styleParagraph(paragraphIndex, references);
    }

    private void styleParagraph(int currentParagraph, List<SyntaxReference> references) {
        clearStyle(currentParagraph);
        boolean isParagraph = true;
        for (SyntaxReference reference : references) {
            if (reference instanceof ParagraphSyntaxReference paragraphSyntaxReference) {
                styleParagraphSyntax(currentParagraph, paragraphSyntaxReference);
                isParagraph = false;
            } else {
                styleInlineSyntax(currentParagraph, reference);
            }
        }
        if (isParagraph) setParagraphStyle(currentParagraph, ParagraphStyle.Paragraph);
    }

    private void styleParagraphSyntax(int currentParagraph, ParagraphSyntaxReference reference) {
        setParagraphStyle(currentParagraph, reference.getParagraphStyle());
        setStyleSpans(currentParagraph, reference.start, reference.getStyleSpans());
    }

    private void styleInlineSyntax(int currentParagraph, SyntaxReference reference) {
        overlayStyleSpans(currentParagraph, reference.start, reference.getStyleSpans());
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
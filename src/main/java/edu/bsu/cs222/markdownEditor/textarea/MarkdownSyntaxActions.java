package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.MarkdownParser;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentOps;
import org.fxmisc.richtext.MultiChangeBuilder;
import org.fxmisc.richtext.StyleActions;
import org.fxmisc.richtext.TextEditingArea;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.TextOps;

public interface MarkdownSyntaxActions extends StyleActions<ParagraphStyle, TextStyle>,
        TextEditingArea<ParagraphStyle, Segment, TextStyle> {

    TextOps<Segment, TextStyle> SEGMENT_OPS = new SegmentOps<>();

    default void styleParagraphMarkdown(int currentParagraph) {
        String text = getText(currentParagraph);
        MarkdownParser parser = new MarkdownParser(text);
        styleParagraphMarkdown(currentParagraph, parser);
    }

    private void styleParagraphMarkdown(int currentParagraph, MarkdownParser parser) {
        clearStyle(currentParagraph);
        parser.getMarkdownSyntax().forEach(syntaxReference -> {
            int start = syntaxReference.start;
            StyleSpans<TextStyle> newStyleSpans = syntaxReference.getStyleSpans();
            StyleSpans<TextStyle> oldStyleSpans = getStyleSpans(start, start + newStyleSpans.length());
            newStyleSpans = oldStyleSpans.overlay(newStyleSpans, TextStyle::overlay);
            setStyleSpans(currentParagraph, start, newStyleSpans);
        });
    }

    default void hideParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        String text = getText(paragraphIndex);
        ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(paragraphIndex);
        new MarkdownParser(text).getMarkdownSyntax().forEach(syntaxReference -> {
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
        parser.getMarkdownSyntax().forEach(syntaxReference -> {
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
        styleParagraphMarkdown(paragraphIndex, parser);
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
package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.parser.MarkdownParser;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentOps;
import org.fxmisc.richtext.MultiChangeBuilder;
import org.fxmisc.richtext.StyleActions;
import org.fxmisc.richtext.TextEditingArea;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
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
        parser.getMarkdownSyntax().forEach(syntaxReference ->
                setStyleSpans(currentParagraph,
                        syntaxReference.start,
                        syntaxReference.getStyleSpans())
        );
    }

    default void hideParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        String text = getText(paragraphIndex);
        new MarkdownParser(text).getMarkdownSyntax().forEach(syntaxReference -> {
            int start = paragraphPosition + syntaxReference.start;
            ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(start);
            for (Segment renderedSegment : syntaxReference.getRenderedSegments()) {
                int end = start + renderedSegment.length();
                System.out.println(getStyleOfChar(start));
                multiChangeBuilder.replace(start,
                        end,
                        ReadOnlyStyledDocument.fromSegment(renderedSegment,
                                paragraphStyle,
                                getStyleOfChar(start),
                                SEGMENT_OPS));
                start = end;
            }
        });
        if (multiChangeBuilder.hasChanges()) multiChangeBuilder.commit();
    }

    default void showParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        String text = getText(paragraphIndex);
        MarkdownParser parser = new MarkdownParser(text);
        parser.getMarkdownSyntax().forEach(syntaxReference -> {
            int start = paragraphPosition + syntaxReference.start;
            ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(start);
            for (Segment renderedSegment : syntaxReference.getMarkdownSegments()) {
                int end = start + renderedSegment.length();
                multiChangeBuilder.replace(start,
                        end,
                        ReadOnlyStyledDocument.fromSegment(renderedSegment,
                                paragraphStyle,
                                TextStyle.EMPTY,
                                SEGMENT_OPS));
                start = end;
            }
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
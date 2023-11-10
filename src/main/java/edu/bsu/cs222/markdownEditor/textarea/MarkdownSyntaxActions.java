package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenSyntaxSegment;
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

    default void hideParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        StyledSegmentReference.createReferences(paragraphPosition, getParagraph(paragraphIndex).getStyledSegments())
                .filter(reference -> reference.style.contains(TextStyle.Property.Markdown))
                .forEach(reference -> {
                    ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(reference.start);
                    multiChangeBuilder.replace(reference.start,
                            reference.end,
                            ReadOnlyStyledDocument.fromSegment(reference.swapSegmentType(),
                                    paragraphStyle,
                                    reference.style,
                                    SEGMENT_OPS));
                });
        if (multiChangeBuilder.hasChanges()) multiChangeBuilder.commit();
    }

    default void showParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Segment, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        StyledSegmentReference.createReferences(paragraphPosition, getParagraph(paragraphIndex).getStyledSegments())
                .filter(reference -> {
                    System.out.println(reference.segment);
                    return reference.segment instanceof HiddenSyntaxSegment;
                })
                .forEach(reference -> {
                    ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(reference.start);
                    multiChangeBuilder.replace(reference.start,
                            reference.end,
                            ReadOnlyStyledDocument.fromSegment(reference.swapSegmentType(),
                                    paragraphStyle,
                                    reference.style,
                                    SEGMENT_OPS));
                });
        if (multiChangeBuilder.hasChanges()) multiChangeBuilder.commit();
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
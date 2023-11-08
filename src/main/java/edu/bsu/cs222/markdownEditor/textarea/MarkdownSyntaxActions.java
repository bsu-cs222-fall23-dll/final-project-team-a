package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.RenderedMarkdownSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.RenderedMarkdownSegmentOps;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegmentOps;
import org.fxmisc.richtext.MultiChangeBuilder;
import org.fxmisc.richtext.StyleActions;
import org.fxmisc.richtext.TextEditingArea;
import org.fxmisc.richtext.model.ReadOnlyStyledDocument;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

import java.util.Optional;

public interface MarkdownSyntaxActions extends StyleActions<ParagraphStyle, TextStyle>,
        TextEditingArea<ParagraphStyle, Either<TextSegment, RenderedMarkdownSegment>, TextStyle> {

    TextOps<TextSegment, TextStyle> TEXT_SEGMENT_OPS = new TextSegmentOps<>();
    TextOps<RenderedMarkdownSegment, TextStyle> RENDERED_MARKDOWN_SEGMENT_OPS = new RenderedMarkdownSegmentOps<>();
    TextOps<Either<TextSegment, RenderedMarkdownSegment>, TextStyle> EITHER_OPS = TEXT_SEGMENT_OPS._or(
            RENDERED_MARKDOWN_SEGMENT_OPS,
            (s1, s2) -> Optional.empty());

    default void hideParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Either<TextSegment, RenderedMarkdownSegment>, TextStyle> multiChangeBuilder = this.createMultiChange();
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
                                    EITHER_OPS));
                });
        if (multiChangeBuilder.hasChanges()) multiChangeBuilder.commit();
    }

    default void showParagraphMarkdown(int paragraphIndex) {
        if (lastParagraphEmpty(paragraphIndex)) return;
        MultiChangeBuilder<ParagraphStyle, Either<TextSegment, RenderedMarkdownSegment>, TextStyle> multiChangeBuilder = this.createMultiChange();
        int paragraphPosition = getParagraphPosition(paragraphIndex);
        StyledSegmentReference.createReferences(paragraphPosition, getParagraph(paragraphIndex).getStyledSegments())
                .filter(reference -> reference.segment.isRight())
                .forEach(reference -> {
                    ParagraphStyle paragraphStyle = getParagraphStyleForInsertionAt(reference.start);
                    multiChangeBuilder.replace(reference.start,
                            reference.end,
                            ReadOnlyStyledDocument.fromSegment(reference.swapSegmentType(),
                                    paragraphStyle,
                                    reference.style,
                                    EITHER_OPS));
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
package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.RenderedMarkdownSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import org.fxmisc.richtext.model.StyledSegment;
import org.reactfx.util.Either;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

class StyledSegmentReference {
    final Either<TextSegment, RenderedMarkdownSegment> segment;
    final TextStyle style;
    final int start;
    final int end;

    private StyledSegmentReference(int start,
            StyledSegment<Either<TextSegment, RenderedMarkdownSegment>, TextStyle> styledSegment) {
        this.start = start;
        this.segment = styledSegment.getSegment();
        this.style = styledSegment.getStyle();
        this.end = start + segment.unify(TextSegment::length, RenderedMarkdownSegment::length);
    }

    static Stream<StyledSegmentReference> createReferences(int paragraphPosition,
            List<StyledSegment<Either<TextSegment, RenderedMarkdownSegment>, TextStyle>> styledSegments) {
        AtomicInteger start = new AtomicInteger(paragraphPosition);
        return styledSegments.stream().map(styledSegment -> {
            StyledSegmentReference reference = new StyledSegmentReference(start.get(), styledSegment);
            start.set(reference.end);
            return reference;
        });
    }

    Either<TextSegment, RenderedMarkdownSegment> swapSegmentType() {
        if (segment.isLeft()) {
            String text = segment.getLeft().getText();
            return Either.right(new RenderedMarkdownSegment(text));
        } else {
            String text = segment.getRight().getText();
            return Either.left(new TextSegment(text));
        }
    }
}
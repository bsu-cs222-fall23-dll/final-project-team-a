package edu.bsu.cs222.markdownEditor.textarea;

import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenSyntaxSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.Segment;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import org.fxmisc.richtext.model.StyledSegment;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

class StyledSegmentReference {
    final Segment segment;
    final TextStyle style;
    final int start;
    final int end;

    private StyledSegmentReference(int start,
            StyledSegment<Segment, TextStyle> styledSegment) {
        this.start = start;
        this.segment = styledSegment.getSegment();
        this.style = styledSegment.getStyle();
        this.end = start + segment.length();
    }

    static Stream<StyledSegmentReference> createReferences(int paragraphPosition,
            List<StyledSegment<Segment, TextStyle>> styledSegments) {
        AtomicInteger start = new AtomicInteger(paragraphPosition);
        return styledSegments.stream().map(styledSegment -> {
            StyledSegmentReference reference = new StyledSegmentReference(start.get(), styledSegment);
            start.set(reference.end);
            return reference;
        });
    }

    Segment swapSegmentType() {
        String text = segment.getText();
        if (segment instanceof HiddenSyntaxSegment) {
            return new TextSegment(text);
        } else {
            return new HiddenSyntaxSegment(text);
        }
    }
}
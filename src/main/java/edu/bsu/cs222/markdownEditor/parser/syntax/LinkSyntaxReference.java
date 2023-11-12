package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.parser.DynamicMatcher;
import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import edu.bsu.cs222.markdownEditor.textarea.segments.HiddenSyntaxSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.HyperlinkSegment;
import edu.bsu.cs222.markdownEditor.textarea.segments.SegmentList;
import edu.bsu.cs222.markdownEditor.textarea.segments.TextSegment;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkSyntaxReference extends SyntaxReference {
    // language=RegExp
    private static final String regexp = "(?<!\\\\)\\[(.+)(?<!\\\\)]\\((.+)(?<!\\\\)\\)";

    public final String text;
    public final String urlString;

    LinkSyntaxReference(Matcher matcher) {
        super(matcher);
        this.text = matcher.group(1);
        this.urlString = matcher.group(2);
    }

    static public void forEachReference(StringBuilder stringBuilder, Consumer<LinkSyntaxReference> action) {
        Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE);
        DynamicMatcher matcher = new DynamicMatcher(pattern, stringBuilder);
        matcher.forEachMatch(match -> action.accept(new LinkSyntaxReference(match)));
    }

    @Override
    public TextStyle getTextStyle() {
        // TODO: Style hyperlinks
        return TextStyle.EMPTY;
    }

    @Override
    public SegmentList getMarkdownSegments() {
        SegmentList map = new SegmentList(start);
        map.add(new TextSegment("["));
        map.skip(text.length());
        map.add(new TextSegment("]("));
        map.add(new HyperlinkSegment(urlString));
        map.add(new TextSegment(")"));
        return map;
    }

    @Override
    public SegmentList getRenderedSegments() {
        SegmentList map = new SegmentList(start);
        map.add(new HiddenSyntaxSegment("["));
        map.add(new HyperlinkSegment(text));
        map.add(new HiddenSyntaxSegment("](" + urlString + ")"));
        return map;
    }

    @Override
    public StyleSpans<TextStyle> getStyleSpans() {
        StyleSpansBuilder<TextStyle> styleSpansBuilder = new StyleSpansBuilder<>();
        styleSpansBuilder.add(TextStyle.MARKDOWN, 1); // [
        styleSpansBuilder.add(getTextStyle(), text.length()); // text
        styleSpansBuilder.add(TextStyle.MARKDOWN.add(TextStyle.Property.Markdown), 2); // ](
        styleSpansBuilder.add(getTextStyle(), urlString.length()); // link
        styleSpansBuilder.add(TextStyle.MARKDOWN.add(TextStyle.Property.Markdown), 1); // )
        return styleSpansBuilder.create();
    }

    public int getTextStartIndex() {
        return start + 1;
    }
}

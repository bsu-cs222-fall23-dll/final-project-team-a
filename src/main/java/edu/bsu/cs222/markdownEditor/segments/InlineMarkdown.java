package edu.bsu.cs222.markdownEditor.segments;

import edu.bsu.cs222.markdownEditor.MarkdownBlock;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InlineMarkdown extends Markdown {

    public InlineMarkdown(String tag) {
        super(tag);
    }

    @Override
    public boolean isPlainText() {
        return false;
    }

    static public void findInstances(MarkdownBlock block, String text) {
        for (Type type : Type.values()) {
            Matcher matcher = type.pattern.matcher(text);
            while (matcher.find()) {
                int start = matcher.start(), end = matcher.end();
                List<Markdown> segments = block.getSegments(start, end);
                boolean isPlainText = segments.stream().allMatch(Markdown::isPlainText);
                if (isPlainText) {
                    InlineMarkdown tagSegment = new InlineMarkdown(matcher.group(1));
                    block.replace(matcher.start(1), matcher.end(1), tagSegment);
                    block.setStyleClass(matcher.end(1), matcher.start(2), type.className);
                    block.replace(matcher.start(2), matcher.end(2), tagSegment);
                }
            }
        }
    }

    private enum Type {
        Italic("[*_]", "i"), Bold("(\\*\\*|__)", "b"), Code("~", "inline-code");

        private final Pattern pattern;
        private final String className;

        Type(String tagRegex, String className) {
            this.pattern = Pattern.compile("(?<!\\\\)(" + tagRegex + ").+?(?<!\\\\)(\\1)(?!\\1)", Pattern.MULTILINE);
            this.className = className;
        }
    }
}

package edu.bsu.cs222.markdownEditor;

import edu.bsu.cs222.markdownEditor.segments.Markdown;
import edu.bsu.cs222.markdownEditor.segments.MarkdownOps;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.TextOps;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;

public class MarkdownEditor extends GenericStyledArea<Void, Markdown, Collection<String>> {

    private static final TextOps<Markdown, Collection<String>> MARKDOWN_OPS = new MarkdownOps<>();

    public MarkdownEditor() {
        super(null, MarkdownEditor::applyParagraphStyle, Collections.emptyList(), MARKDOWN_OPS, Markdown::nodeFactory);
        getStyleClass().add("markdown-editor");
        linkCss();
    }

    private static void applyParagraphStyle(TextFlow textFlow, Void paragraphStyle) {
    }

    private void linkCss() {
        URL url = MarkdownEditor.class.getResource("/markdown.css");
        if (url == null) throw new RuntimeException("Couldn't find CSS file");
        getStylesheets().add(url.toExternalForm());
    }
}

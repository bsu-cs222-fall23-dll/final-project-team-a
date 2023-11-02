package edu.bsu.cs222.markdownEditor;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.SegmentOps;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

public class MarkdownTextArea extends GenericStyledArea<Void, String, Collection<String>> {

    private static final TextOps<String, Collection<String>> STYLED_TEXT_OPS = SegmentOps.styledTextOps();

    public MarkdownTextArea() {
        super(null, MarkdownTextArea::applyParagraphStyle, Collections.emptyList(), STYLED_TEXT_OPS, MarkdownTextArea::nodeFactory);
        getStyleClass().add("markdown-editor");
        linkCss();
    }

    private static void applyParagraphStyle(TextFlow textFlow, Void paragraphStyle) {
    }

    private static Node nodeFactory(StyledSegment<String, Collection<String>> styledSegment) {
        String text = styledSegment.getSegment();
        TextExt textNode = createStyledTextNode(textExt -> textExt.setText(text));
        textNode.getStyleClass().addAll(styledSegment.getStyle());
        return textNode;
    }

    public static TextExt createStyledTextNode(Consumer<TextExt> consumer) {
        TextExt textExt = new TextExt();
        textExt.setTextOrigin(VPos.TOP);
        consumer.accept(textExt);
        return textExt;
    }

    private void linkCss() {
        URL url = MarkdownTextArea.class.getResource("/markdown.css");
        if (url == null) throw new RuntimeException("Couldn't find CSS file");
        getStylesheets().add(url.toExternalForm());
    }
}

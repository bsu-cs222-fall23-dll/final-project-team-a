package edu.bsu.cs222.markdownEditor.parser.syntax;

import edu.bsu.cs222.markdownEditor.textarea.TextStyle;
import javafx.scene.control.IndexRange;

public abstract class SyntaxReference {
    public final IndexRange range;

    SyntaxReference(IndexRange range) {
        this.range = range;
    }

    abstract public TextStyle getTextStyle();
}

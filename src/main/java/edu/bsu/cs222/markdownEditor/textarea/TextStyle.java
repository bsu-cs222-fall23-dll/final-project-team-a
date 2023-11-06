package edu.bsu.cs222.markdownEditor.textarea;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextStyle {
    public static final TextStyle EMPTY = new TextStyle(new HashSet<>());

    private final Set<Property> properties;

    private TextStyle(Set<Property> properties) {
        this.properties = properties;
    }

    public TextStyle add(Property property) {
        TextStyle copy = createCopy();
        copy.properties.add(property);
        return copy;
    }

    public TextStyle concat(TextStyle textStyle) {
        TextStyle copy = createCopy();
        copy.properties.addAll(textStyle.properties);
        return copy;
    }

    private TextStyle createCopy() {
        return new TextStyle(new HashSet<>(properties));
    }

    public List<String> toList() {
        return properties.stream().map(property -> property.className).toList();
    }

    public enum Property {
        Italics("i"), Bold("b"), Code("inline-code");

        private final String className;

        Property(String className) {
            this.className = className;
        }
    }
}

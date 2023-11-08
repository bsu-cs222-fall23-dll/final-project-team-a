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

    public boolean contains(Property property) {
        return properties.contains(property);
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
        Italics("i", "*"), Bold("b", "**"), Code("inline-code", "`"), Markdown("md");

        private final String className;
        public final String defaultTagSyntax;

        Property(String className) {
            this(className, null);
        }

        Property(String className, String defaultTagSyntax) {
            this.className = className;
            this.defaultTagSyntax = defaultTagSyntax;
        }
    }
}

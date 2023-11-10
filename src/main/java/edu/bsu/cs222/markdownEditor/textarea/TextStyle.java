package edu.bsu.cs222.markdownEditor.textarea;

import java.util.Collection;
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

    public TextStyle addAll(Collection<Property> properties) {
        TextStyle copy = createCopy();
        copy.properties.addAll(properties);
        return copy;
    }

    public List<String> toList() {
        return properties.stream().map(property -> property.className).toList();
    }

    private TextStyle createCopy() {
        return new TextStyle(new HashSet<>(properties));
    }

    public enum Property {
        Italics("i", "*"), Bold("b", "**"), Code("inline-code", "`"), Markdown("md");

        public final String defaultTagSyntax;
        private final String className;

        Property(String className) {
            this(className, null);
        }

        Property(String className, String defaultTagSyntax) {
            this.className = className;
            this.defaultTagSyntax = defaultTagSyntax;
        }
    }
}

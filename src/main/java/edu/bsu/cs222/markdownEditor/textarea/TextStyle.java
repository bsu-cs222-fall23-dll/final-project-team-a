package edu.bsu.cs222.markdownEditor.textarea;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextStyle {
    public static final TextStyle EMPTY = new TextStyle(new HashSet<>());
    public static final TextStyle MARKDOWN = EMPTY.add(Property.Markdown);
    public static final TextStyle STRIKETHROUGH = EMPTY.add(Property.Strikethrough);
    public static final TextStyle HIGHLIGHT = EMPTY.add(Property.Highlight);

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

    public TextStyle overlay(TextStyle style) {
        return addAll(style.properties);
    }

    public List<String> toList() {
        return properties.stream().map(property -> property.className).toList();
    }

    private TextStyle createCopy() {
        return new TextStyle(new HashSet<>(properties));
    }

    @Override
    public String toString() {
        String propsString = String.join(", ", properties.stream().map(property -> property.className).toList());
        return String.format("%s[properties = %s]", this.getClass().getName(), propsString);
    }

    public enum Property {
        Italics("i", "*"),
        Bold("b", "**"),
        Code("inline-code", "`"),
        Strikethrough("s", "~~"),
        Highlight("mark", "=="),
        Link("link"),
        Markdown("md");

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

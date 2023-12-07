package edu.bsu.cs222.markdownEditor;

import java.util.prefs.Preferences;

enum UserPreferences {
    FontFamily("Source Code Pro"), StyleSheet("default.css");

    static private final Preferences preferences = Preferences.userNodeForPackage(Main.class);
    private final String defaultValue;

    UserPreferences(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    void setValue(String value) {
        preferences.put(this.name(), value);
    }

    String getValue() {
        return preferences.get(this.name(), defaultValue);
    }
}

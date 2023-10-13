package edu.bsu.cs222.markdownEditor;

import java.io.File;
import java.nio.file.Path;

public class FileManager {
    protected Path activeFilePath;

    public FileManager(Path activeFilePath) {
        this.activeFilePath = activeFilePath;
    }

    public String getActiveFileContents() {
        return null;
    }

    public void open(File file) {}

    public void save(String content) {}

    public void saveAs(String content, File file) {}
}

class NoFileOpenException extends Exception {
    NoFileOpenException() {
        super("No file is open");
    }
}

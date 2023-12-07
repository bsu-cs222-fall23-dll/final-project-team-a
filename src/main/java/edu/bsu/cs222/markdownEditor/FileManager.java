package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    private final AppController appController;
    protected Path activeFilePath;
    private boolean isSaved = false;

    public FileManager(Path activeFilePath){
        this(activeFilePath, null);
    }


    FileManager(Path activeFilePath, AppController appController) {
        this.activeFilePath = activeFilePath;
        this.appController = appController;
    }


    public void setUnsaved() {
        isSaved = false;
    }

    public void save(String content) throws NoFileOpenException {
        if (activeFilePath == null) throw new NoFileOpenException();
        try {
            Files.writeString(activeFilePath, content, StandardCharsets.UTF_8);
            isSaved = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAs(String content, File file) {
        try {
            Files.writeString(file.toPath(), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        open(file);
    }

    public String open(File file) {
        activeFilePath = file.toPath();
        try {
            String text = Files.readString(activeFilePath, StandardCharsets.UTF_8);
            isSaved = true;
            return text;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newFile() {
        if (!isSaved) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/warning-modal.fxml"));
                Scene warningModal = fxmlLoader.load();
                WarningModalController controller = fxmlLoader.getController();
                controller.initialize(appController);
                appController.createModal(warningModal);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.activeFilePath = null;
            setUnsaved();
            appController.clearText();
        }
    }
}

class NoFileOpenException extends Exception {
    NoFileOpenException() {
        super("No file is open");
    }
}

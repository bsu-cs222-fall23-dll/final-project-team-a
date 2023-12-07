package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class PreferenceController {

    private final Path stylesDirectory;
    private AppController appController;

    @FXML private ComboBox<String> fontComboBox;
    @FXML private ComboBox<String> styleSheetComboBox;

    public PreferenceController() {
        URL url = Main.getResourceUrl("/some styles");
        String decodedUrlPath = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
        stylesDirectory = Path.of(decodedUrlPath);
        System.out.println(stylesDirectory);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void initialize() {
        setInitialValues();
        setListeners();
    }

    private void setInitialValues() {
        populateFontComboBox();
        fontComboBox.setValue(UserPreferences.FontFamily.getValue());
        populateStyleSheetComboBox();
        styleSheetComboBox.setValue(UserPreferences.StyleSheet.getValue());
    }

    private void populateFontComboBox() {
        fontComboBox.getItems().add("Source Code Pro");
        fontComboBox.getItems().addAll(Font.getFamilies());
    }

    private void populateStyleSheetComboBox() {
        File[] files = stylesDirectory.toFile().listFiles();
        assert(files != null);
        List<String> fileNames = Arrays.stream(files).map(File::getName).toList();
        styleSheetComboBox.getItems().addAll(fileNames);
    }

    private void setListeners() {
        fontComboBox.valueProperty().addListener(this::handleFontFamilyChange);
        styleSheetComboBox.valueProperty().addListener(this::handleStyleSheetChange);
    }

    private void handleFontFamilyChange(ObservableValue<? extends String> o, String oldValue, String newValue) {
        UserPreferences.FontFamily.setValue(newValue);
        appController.setFontFamily(newValue);
    }

    private void handleStyleSheetChange(ObservableValue<? extends String> o, String oldValue, String newValue) {
        UserPreferences.StyleSheet.setValue(newValue);
        try {
            URL url = stylesDirectory.resolve(newValue).toUri().toURL();
            appController.setEditorCss(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addCustomStyleSheet() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSS files (*.css)", "*.css"));
        File file = fileChooser.showOpenDialog(null);
        if (file.getName().equals("some styles/default.css")) return;
        Path newPath = stylesDirectory.resolve(file.getName());
        try {
            Files.copy(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
            styleSheetComboBox.getItems().add(file.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

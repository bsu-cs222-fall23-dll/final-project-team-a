package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;

public class PreferenceController {

    private AppController appController;

    @FXML private ComboBox<String> fontComboBox;

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
    }

    private void populateFontComboBox() {
        fontComboBox.getItems().add("Source Code Pro");
        fontComboBox.getItems().addAll(Font.getFamilies());
    }

    private void setListeners() {
        fontComboBox.valueProperty().addListener(this::handleFontFamilyChange);
    }

    private void handleFontFamilyChange(ObservableValue<? extends String> o, String oldValue, String newValue) {
        UserPreferences.FontFamily.setValue(newValue);
        appController.setFontFamily(newValue);
    }
}

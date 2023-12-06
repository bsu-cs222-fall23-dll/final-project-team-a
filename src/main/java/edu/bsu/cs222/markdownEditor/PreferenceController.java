package edu.bsu.cs222.markdownEditor;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

public class PreferenceController {

    private AppController appController;

    @FXML private Slider fontSizeSlider;
    @FXML private Label fontSizeLabel;
    @FXML private ComboBox<String> fontComboBox;

    private int currentFontSize;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void initialize() {
        setInitialValues();
        setListeners();
    }

    private void setInitialValues() {
        setInitialFontSize();
        populateFontComboBox();
        fontComboBox.setValue(UserPreferences.FontFamily.getValue());
    }

    private void setInitialFontSize() {
        String fontSizeString = UserPreferences.FontSize.getValue();
        fontSizeLabel.setText(fontSizeString);
        currentFontSize = Integer.parseInt(fontSizeString.substring(0, fontSizeString.length() - 2));
        fontSizeSlider.setValue(currentFontSize);
    }

    private void populateFontComboBox() {
        fontComboBox.getItems().add("Source Code Pro");
        fontComboBox.getItems().addAll(Font.getFamilies());
    }

    private void setListeners() {
        fontSizeSlider.valueProperty().addListener(this::handleFontSizeChange);
        fontComboBox.valueProperty().addListener(this::handleFontFamilyChange);
    }

    private void handleFontSizeChange(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        int newFontSize = newValue.intValue();
        if (currentFontSize != newFontSize) {
            currentFontSize = newFontSize;
            String fontSizeString = newFontSize + "px";
            fontSizeLabel.setText(fontSizeString);
            UserPreferences.FontSize.setValue(fontSizeString);
        }
    }

    private void handleFontFamilyChange(ObservableValue<? extends String> o, String oldValue, String newValue) {
        UserPreferences.FontFamily.setValue(newValue);
        appController.setFontFamily(newValue);
    }
}

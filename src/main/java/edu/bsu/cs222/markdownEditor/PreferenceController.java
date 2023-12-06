package edu.bsu.cs222.markdownEditor;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;

public class PreferenceController {

    @FXML private Slider fontSizeSlider;
    @FXML private Label fontSizeLabel;
    @FXML private ComboBox<String> fontComboBox;

    @FXML
    private void initialize() {
        populateFontComboBox();
        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> handleFontSizeChange(newValue));
    }

    private void populateFontComboBox() {
        String defaultFont = "Source Code Pro";
        fontComboBox.getItems().add(defaultFont);
        fontComboBox.getItems().addAll(Font.getFamilies());
        fontComboBox.setValue(defaultFont);
    }

    private void handleFontSizeChange(Number newValue) {
        fontSizeLabel.setText(newValue.intValue() + "px");
    }
}

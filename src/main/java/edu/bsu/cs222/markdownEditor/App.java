package edu.bsu.cs222.markdownEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1120, 680);
        stage.setTitle("Markdown Editor");
        stage.setScene(scene);
        stage.show();
    }
}
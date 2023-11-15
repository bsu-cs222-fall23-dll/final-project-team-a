package edu.bsu.cs222.markdownEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    public static FileManager fileManager = new FileManager(null);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getResourceUrl("/app.fxml"));
        VBox vBox = fxmlLoader.load();
        Scene scene = new Scene(vBox, 1120, 680);
        scene.getStylesheets().add(getResourceUrl("/markdown.css").toExternalForm());
        stage.setTitle("Markdown Editor");
        stage.setScene(scene);
        stage.show();
    }

    private URL getResourceUrl(String name) {
        URL url = getClass().getResource(name);
        return Objects.requireNonNull(url);
    }

}

package edu.bsu.cs222.markdownEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        Scene scene = new Scene(fxmlLoader.load(), 1120, 680);
        scene.getStylesheets().add(getResourceUrl("/markdown.css").toExternalForm());
        stage.setTitle("Markdown Editor");
        stage.setScene(scene);
        stage.show();
        org.scenicview.ScenicView.show(scene); // for debugging
    }

    private URL getResourceUrl(String name) {
        URL url = getClass().getResource(name);
        return Objects.requireNonNull(url);
    }

}

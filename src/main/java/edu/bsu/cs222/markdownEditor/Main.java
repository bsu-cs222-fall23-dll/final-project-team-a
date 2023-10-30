package edu.bsu.cs222.markdownEditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static FileManager fileManager = new FileManager(null);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        MarkdownBlock markdownBlock = new MarkdownBlock();
//        VirtualizedScrollPane<MarkdownBlock> vsPane = new VirtualizedScrollPane<>(markdownBlock);
//        Scene scene = new Scene(vsPane, 1120, 680);
        Scene scene = new Scene(markdownBlock, 1120, 680);
        stage.setTitle("Markdown Editor");
        stage.setScene(scene);
        stage.show();
        org.scenicview.ScenicView.show(scene);
    }

}

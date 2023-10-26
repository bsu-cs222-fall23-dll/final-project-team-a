package edu.bsu.cs222.markdownEditor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileManagerTest {

    @TempDir
    private Path tempDir;

    private Path createTestFile(String fileName, String content) {
        Path path = tempDir.resolve(fileName);
        if (content == null) content = "";
        try {
            Files.writeString(path, content, StandardCharsets.UTF_8);
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readTestFile(Path path) {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class openFile {
        private final String content = "Hello World!";
        Path path;

        @BeforeEach
        public void setUp() {
            path = createTestFile("openFileTestFile.txt", content);
        }

        @Test
        void setActiveFilePath() {
            FileManager fileManager = new FileManager(null);
            fileManager.open(path.toFile());
            assertEquals(path, fileManager.activeFilePath);
        }

        @Test
        void getsFileContents() {
            FileManager fileManager = new FileManager(path);
            assertEquals(content, fileManager.open(path.toFile()));
        }
    }

    @Nested
    class saveFile {

        @Test
        void writesToFile() throws NoFileOpenException {
            Path path = createTestFile("saveFileTestFile.txt", null);
            FileManager fileManager = new FileManager(path);
            String content = "Hello World!";
            fileManager.save(content);
            assertEquals(readTestFile(path), content);
        }

        @Test
        void throwsNoActiveFileError() {
            FileManager fileManager = new FileManager(null);
            assertThrows(NoFileOpenException.class, () -> fileManager.save(""));
        }
    }


    @Nested
    class saveFileAs {

        @Test
        void createFile() {
            Path path = createTestFile("saveFileAsTest1File.txt", null);
            FileManager fileManager = new FileManager(null);
            String content = "Hello World!";
            fileManager.saveAs(content, path.toFile());
            assertEquals(readTestFile(path), content);
        }

        @Test
        void setActiveFile() {
            Path path = createTestFile("saveFileAsTest2File.txt", null);
            FileManager fileManager = new FileManager(null);
            fileManager.saveAs("", path.toFile());
            assertEquals(path, fileManager.activeFilePath);
        }
    }
}
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LockedMeAppTest {

    private static final String TEST_DIRECTORY_PATH = "C:\\Users\\santh\\Desktop\\Simplilearn_projects\\Final_Project\\test\\"; // Update with the actual test directory path

    @BeforeEach
    void setup() {
        deleteTestDirectory();
        createTestDirectory();
    }
    
    private void createTestDirectory() {
        try {
            Files.createDirectory(Path.of(TEST_DIRECTORY_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteTestDirectory() {
        File directory = new File(TEST_DIRECTORY_PATH);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            directory.delete();
        }
    }

      
    
    @Test
    void testRetrieveFileNames_EmptyDirectory() {
        // Arrange
        String expectedOutput = "No files found in the directory.";

        // Act
        String actualOutput = retrieveFileNames();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testRetrieveFileNames_NonEmptyDirectory() {
        // Arrange
        List<String> fileNames = new ArrayList<>();
        fileNames.add("file1.txt");
        fileNames.add("file2.txt");
        fileNames.add("file3.txt");
        prepareTestDirectory(fileNames);

        // Act
        String actualOutput = retrieveFileNames();

        // Assert
        Assertions.assertTrue(actualOutput.contains("file1.txt"));
        Assertions.assertTrue(actualOutput.contains("file2.txt"));
        Assertions.assertTrue(actualOutput.contains("file3.txt"));
    }

    @Test
    void testRetrieveFileNames_InvalidDirectory() {
        // Arrange
        String expectedOutput = "No files found in the directory.";
        prepareTestDirectory(null);

        // Act
        String actualOutput = retrieveFileNames();

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
    
    
    private void prepareTestDirectory(List<String> fileNames) {
        File directory = new File(TEST_DIRECTORY_PATH);
        directory.mkdirs();

        if (fileNames != null) {
            for (String fileName : fileNames) {
                File file = new File(directory, fileName);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String retrieveFileNames() {
        List<String> fileNames = LockedMeApp.getFileNamesInAscendingOrder();
        StringBuilder output = new StringBuilder();

        if (fileNames.isEmpty()) {
            output.append("No files found in the directory.");
        } else {
            for (String fileName : fileNames) {
                output.append(fileName).append("\n");
            }
        }

        return output.toString();
    }
}

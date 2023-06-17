import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class LockedMeAppTest {
    private static final String TEST_DIRECTORY_PATH = "C:\\Users\\santh\\Desktop\\Simplilearn_projects\\Final_Project\\test"; // Update with the actual test directory path
    private static final String TEST_FILE_NAME = "testFile.txt";

    private File testDirectory;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setup() {
        testDirectory = new File(TEST_DIRECTORY_PATH);
        testDirectory.mkdir();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
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
        List<String> expectedFileNames = Arrays.asList();
        List<String> actualFileNames = LockedMeApp.getFileNamesInAscendingOrder();

        Assertions.assertEquals(expectedFileNames, actualFileNames);
    }

    @Test
    void testRetrieveFileNames_NonEmptyDirectory() {
        createTestFile(TEST_FILE_NAME);

        List<String> expectedFileNames = Arrays.asList(TEST_FILE_NAME);
        List<String> actualFileNames = LockedMeApp.getFileNamesInAscendingOrder();

        Assertions.assertEquals(expectedFileNames, actualFileNames);
    }

    @Test
    void testAddFile_FileAddedSuccessfully() {
        provideUserInput(TEST_FILE_NAME + System.lineSeparator());

        LockedMeApp.addFile();

        String expectedOutput = "File added successfully.";
        String actualOutput = outputStream.toString().trim();

        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertTrue(fileExists(TEST_FILE_NAME));
    }

    @Test
    void testAddFile_FileAlreadyExists() {
        createTestFile(TEST_FILE_NAME);
        provideUserInput(TEST_FILE_NAME + System.lineSeparator());

        LockedMeApp.addFile();

        String expectedOutput = "File already exists in the directory.";
        String actualOutput = outputStream.toString().trim();

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testDeleteFile_FileDeletedSuccessfully() {
        createTestFile(TEST_FILE_NAME);
        provideUserInput(TEST_FILE_NAME + System.lineSeparator());

        LockedMeApp.deleteFile();

        String expectedOutput = "File deleted successfully.";
        String actualOutput = outputStream.toString().trim();

        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertFalse(fileExists(TEST_FILE_NAME));
    }

    @Test
    void testDeleteFile_FileNotFound() {
        provideUserInput(TEST_FILE_NAME + System.lineSeparator());

        LockedMeApp.deleteFile();

        String expectedOutput = "File not found.";
        String actualOutput = outputStream.toString().trim();

        Assertions.assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    void testSearchFile_FileFound() {
        createTestFile(TEST_FILE_NAME);
        provideUserInput(TEST_FILE_NAME + System.lineSeparator());

        LockedMeApp.searchFile();

        String expectedOutput = "File found: " + TEST_DIRECTORY_PATH + File.separator + TEST_FILE_NAME;
        String actualOutput = outputStream.toString().trim();

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testSearchFile_FileNotFound() {
        provideUserInput(TEST_FILE_NAME + System.lineSeparator());

        LockedMeApp.searchFile();

        String expectedOutput = "File not found.";
        String actualOutput = outputStream.toString().trim();

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testGetUserChoice_ValidChoice() {
        provideUserInput("2" + System.lineSeparator());

        int minChoice = 1;
        int maxChoice = 3;
        int expectedChoice = 2;
        int actualChoice = LockedMeApp.getUserChoice(minChoice, maxChoice);

        Assertions.assertEquals(expectedChoice, actualChoice);
    }

    @Test
    void testGetUserChoice_InvalidChoiceThenValidChoice() {
        provideUserInput("invalid" + System.lineSeparator() + "2" + System.lineSeparator());

        int minChoice = 1;
        int maxChoice = 3;
        int expectedChoice = 2;
        int actualChoice = LockedMeApp.getUserChoice(minChoice, maxChoice);

        Assertions.assertEquals(expectedChoice, actualChoice);
    }

    private void createTestFile(String fileName) {
        File file = new File(testDirectory, fileName);
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean fileExists(String fileName) {
        File file = new File(testDirectory, fileName);
        return file.exists();
    }

    private void provideUserInput(String userInput) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);
    }
}

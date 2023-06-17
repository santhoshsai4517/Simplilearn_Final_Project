import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LockedMeApp {
    private static final String DIRECTORY_PATH = "C:\\Users\\santh\\Desktop\\Simplilearn_projects\\Final_Project\\test"; // Update with the actual directory path

    public static void main(String[] args) {
        displayWelcomeScreen();
        showMainMenu();
    }

    private static void displayWelcomeScreen() {
        System.out.println("=====================================");
        System.out.println("        Welcome to LockedMe.com       ");
        System.out.println("        Developed by Santhosh       ");
        System.out.println("=====================================");
    }

    private static void showMainMenu() {
        System.out.println("\n[MAIN MENU]");
        System.out.println("1. Retrieve file names in ascending order");
        System.out.println("2. Manage files");
        System.out.println("3. Close the application");

        int choice = getUserChoice(1, 3);

        switch (choice) {
            case 1:
                retrieveFileNames();
                break;
            case 2:
                showFileManagementMenu();
                break;
            case 3:
                closeApplication();
                break;
        }
    }

    private static void retrieveFileNames() {
        List<String> fileNames = getFileNamesInAscendingOrder();
        System.out.println("\n[FILE NAMES IN ASCENDING ORDER]");
        if (fileNames.isEmpty()) {
            System.out.println("No files found in the directory.");
        } else {
            for (String fileName : fileNames) {
                System.out.println(fileName);
            }
        }
        showMainMenu();
    }

    static List<String> getFileNamesInAscendingOrder() {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles();
        List<String> fileNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
            Collections.sort(fileNames);
        }

        return fileNames;
    }

    private static void showFileManagementMenu() {
        System.out.println("\n[FILE MANAGEMENT MENU]");
        System.out.println("1. Add a file to the directory");
        System.out.println("2. Delete a file from the directory");
        System.out.println("3. Search for a file in the directory");
        System.out.println("4. Navigate back to the main menu");

        int choice = getUserChoice(1, 4);

        switch (choice) {
            case 1:
                addFile();
                break;
            case 2:
                deleteFile();
                break;
            case 3:
                searchFile();
                break;
            case 4:
                showMainMenu();
                break;
        }
    }

    static void addFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the name of the file to add: ");
        String fileName = scanner.nextLine();

        File file = new File(DIRECTORY_PATH + File.separator + fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File added successfully.");
            } else {
                System.out.println("File already exists in the directory.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding the file: " + e.getMessage());
        }

        showFileManagementMenu();
    }

    static void deleteFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the name of the file to delete: ");
        String fileName = scanner.nextLine();

        File file = new File(DIRECTORY_PATH + File.separator + fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File not found.");
        }

        showFileManagementMenu();
    }

    static void searchFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the name of the file to search: ");
        String fileName = scanner.nextLine();

        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    System.out.println("File found: " + file.getAbsolutePath());
                    showFileManagementMenu();
                    return;
                }
            }
        }

        System.out.println("File not found.");
        showFileManagementMenu();
    }

    private static void closeApplication() {
        System.out.println("\nClosing the application...");
        System.exit(0);
    }

    static int getUserChoice(int minChoice, int maxChoice) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.print("Enter your choice (" + minChoice + "-" + maxChoice + "): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a valid choice: ");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < minChoice || choice > maxChoice);

        return choice;
    }
}

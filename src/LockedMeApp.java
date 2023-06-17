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
        System.out.println("      Developed by [Your Name]       ");
        System.out.println("=====================================");
    }

    private static void showMainMenu() {
        System.out.println("\n[MAIN MENU]");
        System.out.println("1. Retrieve file names in ascending order");
        System.out.println("2. Close the application");

        int choice = getUserChoice(1, 2);

        switch (choice) {
            case 1:
                retrieveFileNames();
                break;
            case 2:
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

    private static void closeApplication() {
        System.out.println("\nClosing the application...");
        System.exit(0);
    }

    private static int getUserChoice(int minChoice, int maxChoice) {
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

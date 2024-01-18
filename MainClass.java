import java.util.Scanner;

public class MainClass{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int maxIndividuals = 30;
        String[] names = new String[maxIndividuals];
        String[] accounts = new String[maxIndividuals];
        boolean[] attendanceStatus = new boolean[maxIndividuals];
        int[] daysPresent = new int[maxIndividuals];
        int[] daysAbsent = new int[maxIndividuals];
        boolean[] isLate = new boolean[maxIndividuals];
        String[] lateTimes = new String[maxIndividuals];

        while (true) {
            System.out.println("Attendance Management System");
            System.out.println("1. Create Account");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View Attendance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createAccount(names, accounts);
                    break;
                case 2:
                    markAttendance(names, attendanceStatus, daysPresent, daysAbsent, isLate, lateTimes);
                    break;
                case 3:
                    viewAttendance(names, attendanceStatus, daysPresent, daysAbsent, isLate, lateTimes);
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void createAccount(String[] names, String[] accounts) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the individual: ");
        String name = scanner.nextLine();

        System.out.print("Enter the account information for " + name + ": ");
        String accountInfo = scanner.nextLine();

        for (int i = 0; i < names.length; i++) {
            if (names[i] == null) {
                names[i] = name;
                accounts[i] = accountInfo;
                System.out.println("Account created for " + name);
                break;
            }
        }
    }

    private static void markAttendance(String[] names, boolean[] attendanceStatus, int[] daysPresent, int[] daysAbsent, boolean[] isLate, String[] lateTimes) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("List of Individuals:");
        displayIndividuals(names);

        System.out.print("Enter the name of the individual: ");
        String name = scanner.nextLine();

        int index = findIndexByName(names, name);

        if (index == -1) {
            System.out.println("Individual not found. Please enter a valid name.");
            return;
        }

        System.out.print("Is " + name + " present? (yes/no): ");
        String presence = scanner.nextLine().toLowerCase();

        if (presence.equals("yes")) {
            attendanceStatus[index] = true;
            daysPresent[index]++;
        } else if (presence.equals("no")) {
            attendanceStatus[index] = false;
            daysAbsent[index]++;
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            return;
        }

        System.out.print("Is " + name + " late? (yes/no): ");
        String isLateInput = scanner.nextLine().toLowerCase();

        if (isLateInput.equals("yes")) {
            isLate[index] = true;
            System.out.print("Enter the late time for " + name + " (e.g., 09:15 AM/PM): ");
            String lateTime = scanner.nextLine();
            
            // Check if the time is in AM or PM format
            if (isValidTimeFormat(lateTime)) {
                lateTimes[index] = lateTime;
                System.out.println("Attendance marked for " + name);
            } else {
                System.out.println("Invalid time format. Please use HH:mm AM/PM.");
                // Reset the attendance status and late information
                attendanceStatus[index] = false;
                daysAbsent[index]++;
                isLate[index] = false;
                lateTimes[index] = null;
            }
        } else {
            isLate[index] = false;
            System.out.println("Attendance marked for " + name);
        }
    }

    private static void viewAttendance(String[] names, boolean[] attendanceStatus, int[] daysPresent, int[] daysAbsent, boolean[] isLate, String[] lateTimes) {
        System.out.println("Attendance Status:");
        for (int i = 0; i < names.length; i++) {
            if (names[i] != null) {
                System.out.println(names[i] + ": " + (attendanceStatus[i] ? "Present" : "Absent")
                        + " (Present Days: " + daysPresent[i] + ", Absent Days: " + daysAbsent[i]
                        + ", Late: " + (isLate[i] ? "Yes" : "No") + ", Late Time: " + lateTimes[i] + ")");
            }
        }
    }

    private static void displayIndividuals(String[] names) {
        System.out.println("List of Individuals:");
        for (String name : names) {
            if (name != null) {
                System.out.println(name);
            }
        }
    }

    private static int findIndexByName(String[] names, String targetName) {
        for (int i = 0; i < names.length; i++) {
            if (names[i] != null && names[i].equalsIgnoreCase(targetName)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isValidTimeFormat(String time) {
        // Validate the time format (HH:mm AM/PM)
        return time.matches("(1[0-2]|0[1-9]):[0-5][0-9] (AM|PM|am|pm)");
    }
}

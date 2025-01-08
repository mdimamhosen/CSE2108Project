package university;
import java.sql.*;
import java.util.Scanner;

public class UniversityManagement {

    private static Scanner scanner = new Scanner(System.in);
    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = DBUtil.getConnection();
            System.out.println("Welcome to University Management System");
            login();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void login() throws SQLException {
        System.out.println("Select Login Role:");
        System.out.println("1. Admin");
        System.out.println("2. Teacher");
        System.out.println("3. Student");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline left by nextInt()

        String username, password;
        boolean loginSuccess = false;

        switch (roleChoice) {
            case 1:  // Admin login
                System.out.print("Enter Admin Username: ");
                username = scanner.nextLine();
                System.out.print("Enter Admin Password: ");
                password = scanner.nextLine();

                loginSuccess = loginAdmin(username, password);
                break;
            case 2:  // Teacher login
                System.out.print("Enter Teacher Name: ");
                username = scanner.nextLine();
                System.out.print("Enter Teacher Password: ");
                password = scanner.nextLine();

                loginSuccess = loginTeacher(username, password);
                break;
            case 3:  // Student login
                System.out.print("Enter Student Name: ");
                username = scanner.nextLine();
                System.out.print("Enter Student Password: ");
                password = scanner.nextLine();

                loginSuccess = loginStudent(username, password);
                break;
            case 4:  // Exit
                System.out.println("Exiting system...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
                return; // Exit the login method if an invalid option is selected
        }

        if (loginSuccess) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static boolean loginAdmin(String username, String password) throws SQLException {
        String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                System.out.println("Login successful as " + role);
                adminMenu();
                return true;
            }
        }
        return false;
    }

    private static boolean loginTeacher(String username, String password) throws SQLException {
        String query = "SELECT * FROM Teachers WHERE name = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                System.out.println("Login successful as " + role);
                teacherMenu();
                return true;
            }
        }
        return false;
    }

    private static boolean loginStudent(String username, String password) throws SQLException {
        String query = "SELECT * FROM Students WHERE name = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = "student";  // Role hardcoded for students
                System.out.println("Login successful as " + role);
                studentMenu();
                return true;
            }
        }
        return false;
    }

    private static void adminMenu() throws SQLException {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Teacher");
            System.out.println("2. Add Student");
            System.out.println("3. View All Students");
            System.out.println("4. Manage Courses");
            System.out.println("5. Manage Hostels");
            System.out.println("6. Manage Clubs");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1: addTeacher(); break;
                case 2: addStudent(); break;
                case 3: viewAllStudents(); break;
                case 4: manageCourses(); break;
                case 5: manageHostels(); break;
                case 6: manageClubs(); break;
                case 7: System.exit(0); break;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private static void teacherMenu() throws SQLException {
        while (true) {
            System.out.println("\nTeacher Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Enrolled Students");
            System.out.println("3. Manage Courses");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewEnrolledStudents(); break;
                case 3: manageCourses(); break;
                case 4: System.exit(0); break;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private static void studentMenu() {
        while (true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View Courses");
            System.out.println("2. View Grades");
            System.out.println("3. Join Clubs");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1: viewCourses(); break;
                case 2: viewGrades(); break;
                case 3: joinClubs(); break;
                case 4: System.exit(0); break;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private static void viewAllStudents() throws SQLException {
        String query = "SELECT * FROM Students";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("\nStudents List:");
            System.out.println(String.format("%-5s %-20s %-5s %-15s", "ID", "Name", "Age", "Department"));
            System.out.println("-----------------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("%-5d %-20s %-5d %-15d", rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getInt("department_id")));
            }
        }
    }

    private static void addTeacher() throws SQLException {
        System.out.print("Enter Teacher Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department ID: ");
        int deptId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        String query = "INSERT INTO Teachers (name, department_id, password, role) VALUES (?, ?, ?, 'teacher')";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, deptId);
            stmt.setString(3, password);
            stmt.executeUpdate();
            System.out.println("Teacher added successfully.");
        }
    }

    private static void addStudent() throws SQLException {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Department ID: ");
        int deptId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Hostel ID: ");
        int hostelId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        String query = "INSERT INTO Students (name, age, department_id, password, hostel_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setInt(3, deptId);
            stmt.setString(4, password);
            stmt.setInt(5, hostelId);
            stmt.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }

    private static void viewEnrolledStudents() {
        // Add the logic for viewing enrolled students
    }

    private static void manageCourses() {
        // Add methods for course management
    }

    private static void manageHostels() {
        // Add methods for hostel management
    }

    private static void manageClubs() {
        // Add methods for club management
    }

    private static void viewCourses() {
        // View courses for students
    }

    private static void viewGrades() {
        // View grades for students
    }

    private static void joinClubs() {
        // Join clubs for students
    }
}

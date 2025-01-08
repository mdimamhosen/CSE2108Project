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
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Teacher");
            System.out.println("2. Add Student");
            System.out.println("3. View All Students");
            System.out.println("4. Manage Courses");
            System.out.println("5. Manage Hostels");
            System.out.println("6. Manage Clubs");
            System.out.println("7. Add Department");
            System.out.println("8. View All Teachers");
            System.out.println("9. View All Departments");
            System.out.println("10. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addTeacher();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    viewAllStudents();
                    break;
                case 4:
                    manageCourses();
                    break;
                case 5:
                    manageHostels();
                    break;
                case 6:
                    manageClubs();
                    break;
                case 7:
                    addDepartment();
                    break;
                case 8:
                    viewAllTeachers();  // View all teachers option
                    break;
                case 9:
                    viewAllDepartments();  // View all departments option
                    break;
                case 10:
                    System.out.println("Exiting Admin Menu.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
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
    private static void viewAllTeachers() throws SQLException {
        String query = "SELECT * FROM teachers";  // SQL query to get all teachers

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("List of Teachers:");
            while (rs.next()) {
                int teacherId = rs.getInt("id");
                String name = rs.getString("name");
                int departmentId = rs.getInt("department_id");
                System.out.println("Teacher ID: " + teacherId + ", Name: " + name + ", Department ID: " + departmentId);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving teachers: " + e.getMessage());
        }
    }
    private static void viewAllDepartments() throws SQLException {
        String query = "SELECT * FROM departments";  // SQL query to get all departments

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("List of Departments:");
            while (rs.next()) {
                int departmentId = rs.getInt("id");
                String departmentName = rs.getString("name");
                System.out.println("Department ID: " + departmentId + ", Name: " + departmentName);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving departments: " + e.getMessage());
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
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Teacher Name: ");
        String teacherName = scanner.nextLine();
        System.out.print("Enter Department ID: ");
        int departmentId = scanner.nextInt();
        System.out.print("Enter Password: ");
        String teacherPassword = scanner.next();

        try {
            // Check if the department with the provided ID exists
            String checkDeptQuery = "SELECT COUNT(*) FROM departments WHERE id = ?";
            PreparedStatement checkDeptStmt = conn.prepareStatement(checkDeptQuery);
            checkDeptStmt.setInt(1, departmentId);
            ResultSet resultSet = checkDeptStmt.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Department exists, proceed with adding teacher
                String insertTeacherQuery = "INSERT INTO teachers (name, department_id, password) VALUES (?, ?, ?)";
                PreparedStatement insertTeacherStmt = conn.prepareStatement(insertTeacherQuery);
                insertTeacherStmt.setString(1, teacherName);
                insertTeacherStmt.setInt(2, departmentId);
                insertTeacherStmt.setString(3, teacherPassword);

                int rowsAffected = insertTeacherStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Teacher added successfully.");
                } else {
                    System.out.println("Failed to add teacher.");
                }
            } else {
                // Department doesn't exist
                System.out.println("Error: Department with ID " + departmentId + " does not exist.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding teacher: " + e.getMessage());
        }
    }

    private static void addDepartment() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Department Name: ");
        String departmentName = scanner.nextLine();

        try {
            // Insert new department
            String insertDeptQuery = "INSERT INTO departments (name) VALUES (?)";
            PreparedStatement insertDeptStmt = conn.prepareStatement(insertDeptQuery);
            insertDeptStmt.setString(1, departmentName);

            int rowsAffected = insertDeptStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Department added successfully.");
            } else {
                System.out.println("Failed to add department.");
            }

        } catch (SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
        }
    }


    private static void addStudent() throws SQLException {
        String name;
        int age = 0, deptId = 0, hostelId = 0;
        String password;

        // Input validation for student name
        System.out.print("Enter Student Name: ");
        name = scanner.nextLine();

        // Input validation for age (Ensure it's a valid integer)
        while (true) {
            System.out.print("Enter Age: ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number for age.");
                scanner.nextLine();  // Consume the invalid input
            }
        }

        // Input validation for department ID (Ensure it's a valid integer)
        while (true) {
            System.out.print("Enter Department ID: ");
            if (scanner.hasNextInt()) {
                deptId = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number for Department ID.");
                scanner.nextLine();  // Consume the invalid input
            }
        }

        // Input validation for hostel ID (Ensure it's a valid integer)
        while (true) {
            System.out.print("Enter Hostel ID: ");
            if (scanner.hasNextInt()) {
                hostelId = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number for Hostel ID.");
                scanner.nextLine();  // Consume the invalid input
            }
        }

        // Check if hostel exists in the database
        if (!isHostelExists(hostelId)) {
            System.out.println("Hostel with ID " + hostelId + " does not exist. Please add the hostel first.");
            return;  // Exit the method if hostel doesn't exist
        }

        // Input for password
        scanner.nextLine();  // Consume the newline character left by nextInt()
        System.out.print("Enter Password: ");
        password = scanner.nextLine();

        // Proceed with student addition
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

    // Method to check if hostel exists in the database
    private static boolean isHostelExists(int hostelId) {
        String query = "SELECT COUNT(*) FROM Hostels WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, hostelId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Return true if hostel exists, false otherwise
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the SQLException (for example, print the stack trace)
        }
        return false;  // Return false if query fails or hostel does not exist
    }



    private static void viewEnrolledStudents() {
        // Add the logic for viewing enrolled students
    }

    private static void manageCourses() {
        // Add methods for course management
    }

    private static void manageHostels() {
        while (true) {
            System.out.println("\nHostel Management Menu:");
            System.out.println("1. Add Hostel");
            System.out.println("2. Delete Hostel");
            System.out.println("3. Edit Hostel");
            System.out.println("4. View All Hostels");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addHostel();
                    break;
                case 2:
                    deleteHostel();
                    break;
                case 3:
                    editHostel();
                    break;
                case 4:
                    viewAllHostels();
                    break;
                case 5:
                    return;  // Exit to admin menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addHostel() {
        scanner.nextLine(); // Consume the newline character left by nextInt()

        System.out.print("Enter Hostel Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Hostel Capacity: ");
        int capacity = scanner.nextInt();

        System.out.print("Enter Hostel Address: ");
        scanner.nextLine(); // Consume newline
        String address = scanner.nextLine();

        String query = "INSERT INTO Hostels (name, capacity, address) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, capacity);
            stmt.setString(3, address);
            stmt.executeUpdate();
            System.out.println("Hostel added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding hostel: " + e.getMessage());
        }
    }

    private static void deleteHostel() {
        System.out.print("Enter Hostel ID to delete: ");
        int hostelId = scanner.nextInt();

        // Check if the hostel exists before trying to delete
        if (!isHostelExists(hostelId)) {
            System.out.println("Hostel with ID " + hostelId + " does not exist.");
            return;
        }

        String query = "DELETE FROM Hostels WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, hostelId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Hostel deleted successfully.");
            } else {
                System.out.println("Hostel not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting hostel: " + e.getMessage());
        }
    }

    private static void editHostel() {
        System.out.print("Enter Hostel ID to edit: ");
        int hostelId = scanner.nextInt();

        // Check if the hostel exists before editing
        if (!isHostelExists(hostelId)) {
            System.out.println("Hostel with ID " + hostelId + " does not exist.");
            return;
        }

        scanner.nextLine(); // Consume the newline character left by nextInt()

        System.out.print("Enter new Hostel Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new Hostel Capacity: ");
        int capacity = scanner.nextInt();

        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Hostel Address: ");
        String address = scanner.nextLine();

        String query = "UPDATE Hostels SET name = ?, capacity = ?, address = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, capacity);
            stmt.setString(3, address);
            stmt.setInt(4, hostelId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Hostel details updated successfully.");
            } else {
                System.out.println("Error updating hostel.");
            }
        } catch (SQLException e) {
            System.out.println("Error editing hostel: " + e.getMessage());
        }
    }

    private static void viewAllHostels() {
        String query = "SELECT * FROM Hostels";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nHostel List:");
            System.out.println("ID    Name           Capacity   Address");
            System.out.println("-----------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                String address = rs.getString("address");
                System.out.printf("%-6d %-15s %-10d %s\n", id, name, capacity, address);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing hostels: " + e.getMessage());
        }
    }

//    private static boolean isHostelExists(int hostelId) throws SQLException {
//        String query = "SELECT COUNT(*) FROM Hostels WHERE id = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, hostelId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;  // Return true if hostel exists, false otherwise
//            }
//        }
//        return false;  // Return false if query fails or hostel does not exist
//    }


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

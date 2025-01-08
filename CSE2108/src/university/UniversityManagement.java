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

    private static void adminMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Manage Students");
                System.out.println("2. Manage Teachers");
                System.out.println("3. Manage Hostels");
                System.out.println("4. Manage Department");
                System.out.println("5. Exit");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        manageStudents();  // Manage Students submenu
                        break;
                    case 2:
                        manageTeachers();  // Manage Teachers submenu
                        break;

                    case 3:
                        manageHostels();
                        break;
                    case 4:
                        manageDepartment();  // Manage Department submenu
                        break;
                    case 5:
                        System.out.println("Exiting Admin Menu.");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }


    // Manage Department Submenu
    private static void manageDepartment() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nManage Department:");
                System.out.println("1. Add Department");

                System.out.println("2. View All Departments");
                System.out.println("3. Back to Admin Menu");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addDepartment();
                        break;
                    case 2:
                        viewAllDepartments();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while processing department data: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void editDepartment() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the Department ID to edit: ");
            int departmentId = scanner.nextInt();

            // Check if the department exists
            if (!isDepartmentExists(departmentId)) {
                System.out.println("Department with ID " + departmentId + " does not exist.");
                return;
            }

            // Fetch current department details
            String currentName = null;
            String currentHead = null;

            String fetchQuery = "SELECT name, head FROM Departments WHERE id = ?";
            try (PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery)) {
                fetchStmt.setInt(1, departmentId);
                ResultSet rs = fetchStmt.executeQuery();
                if (rs.next()) {
                    currentName = rs.getString("name");
                    currentHead = rs.getString("head");
                }
            }

            // Input new details (optional updates)
            scanner.nextLine(); // Consume newline

            System.out.print("Enter new name for the department (current: " + currentName + "): ");
            String newName = scanner.nextLine();
            if (newName.isEmpty()) {
                newName = currentName; // Retain current value
            }

            System.out.print("Enter new head for the department (current: " + currentHead + "): ");
            String newHead = scanner.nextLine();
            if (newHead.isEmpty()) {
                newHead = currentHead; // Retain current value
            }

            // Update the department in the database
            String query = "UPDATE Departments SET name = ?, head = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newName);
                stmt.setString(2, newHead);
                stmt.setInt(3, departmentId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Department updated successfully.");
                } else {
                    System.out.println("Failed to update the department.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while editing the department: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void deleteDepartment() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the Department ID to delete: ");
            int departmentId = scanner.nextInt();

            // Check if the department exists
            if (!isDepartmentExists(departmentId)) {
                System.out.println("Department with ID " + departmentId + " does not exist.");
                return;
            }

            // Confirm deletion
            System.out.print("Are you sure you want to delete this department? (yes/no): ");
            scanner.nextLine(); // Consume newline
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("Department deletion cancelled.");
                return;
            }

            // Delete the department
            String query = "DELETE FROM Departments WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, departmentId);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Department deleted successfully.");
                } else {
                    System.out.println("Failed to delete the department.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting the department: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Utility method to check if a department exists
    private static boolean isDepartmentExists(int departmentId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Departments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }




    // Manage Students Submenu
    private static void manageStudents() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nManage Students:");
                System.out.println("1. Add Student");
                System.out.println("2. Edit Student");
                System.out.println("3. Delete Student");
                System.out.println("4. View All Students");
                System.out.println("5. Back to Admin Menu");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        editStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        viewAllStudents();
                        break;
                    case 5:
                        return;  // Go back to the Admin Menu
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while processing student data: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
    private static boolean isStudentExists(int studentId) {
        try {
            String query = "SELECT COUNT(*) FROM Students WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, studentId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if the student exists
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking student existence: " + e.getMessage());
        }
        return false;
    }

    // Manage Teachers Submenu
    private static void manageTeachers() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nManage Teachers:");
                System.out.println("1. Add Teacher");
                System.out.println("2. Edit Teacher");
                System.out.println("3. Delete Teacher");
                System.out.println("4. View All Teachers");
                System.out.println("5. Back to Admin Menu");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addTeacher();
                        break;
                    case 2:
                        editTeacher();
                        break;
                    case 3:
                        deleteTeacher();
                        break;
                    case 4:
                        viewAllTeachers();
                        break;
                    case 5:
                        return;  // Go back to the Admin Menu
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while processing teacher data: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }



    private static void editStudent() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the ID of the student to edit: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Check if the student exists
            if (!isStudentExists(studentId)) {
                System.out.println("Student with ID " + studentId + " does not exist.");
                return;
            }

            System.out.print("Enter new name (or press Enter to skip): ");
            String name = scanner.nextLine();

            System.out.print("Enter new age (or press Enter to skip): ");
            String ageInput = scanner.nextLine();

            System.out.print("Enter new department ID (or press Enter to skip): ");
            String departmentIdInput = scanner.nextLine();

            // Construct query with optional fields
            String query = "UPDATE Students SET "
                    + (name.isEmpty() ? "" : "name = ?, ")
                    + (ageInput.isEmpty() ? "" : "age = ?, ")
                    + (departmentIdInput.isEmpty() ? "" : "department_id = ? ")
                    + "WHERE id = ?";
            query = query.replace(", WHERE", " WHERE"); // Handle trailing comma

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                int paramIndex = 1;

                if (!name.isEmpty()) {
                    stmt.setString(paramIndex++, name);
                }
                if (!ageInput.isEmpty()) {
                    stmt.setInt(paramIndex++, Integer.parseInt(ageInput));
                }
                if (!departmentIdInput.isEmpty()) {
                    stmt.setInt(paramIndex++, Integer.parseInt(departmentIdInput));
                }
                stmt.setInt(paramIndex, studentId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Student details updated successfully.");
                } else {
                    System.out.println("Failed to update student details.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Age and department ID must be valid numbers.");
        } catch (SQLException e) {
            System.out.println("Database error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the ID of the student to delete: ");
            int studentId = scanner.nextInt();

            // Check if the student exists
            if (!isStudentExists(studentId)) {
                System.out.println("Student with ID " + studentId + " does not exist.");
                return;
            }

            System.out.print("Are you sure you want to delete this student? (yes/no): ");
            String confirmation = scanner.next();
            if (!confirmation.equalsIgnoreCase("yes")) {
                System.out.println("Deletion canceled.");
                return;
            }

            String query = "DELETE FROM Students WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, studentId);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Student deleted successfully.");
                } else {
                    System.out.println("Failed to delete student.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }




    private static void editTeacher() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the Teacher ID to edit: ");
            int teacherId = scanner.nextInt();

            // Check if the teacher exists
            if (!isTeacherExists(teacherId)) {
                System.out.println("Teacher with ID " + teacherId + " does not exist.");
                return;
            }

            // Input new details
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new name for the teacher: ");
            String newName = scanner.nextLine();

            System.out.print("Enter new age for the teacher: ");
            int newAge = scanner.nextInt();

            System.out.print("Enter new department ID for the teacher: ");
            int newDepartmentId = scanner.nextInt();

            // Update the teacher in the database
            String query = "UPDATE Teachers SET name = ?, age = ?, department_id = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newName);
                stmt.setInt(2, newAge);
                stmt.setInt(3, newDepartmentId);
                stmt.setInt(4, teacherId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Teacher updated successfully.");
                } else {
                    System.out.println("Failed to update the teacher.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while editing the teacher: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void deleteTeacher() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the Teacher ID to delete: ");
            int teacherId = scanner.nextInt();

            // Check if the teacher exists
            if (!isTeacherExists(teacherId)) {
                System.out.println("Teacher with ID " + teacherId + " does not exist.");
                return;
            }

            // Delete the teacher from the database
            String query = "DELETE FROM Teachers WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, teacherId);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Teacher deleted successfully.");
                } else {
                    System.out.println("Failed to delete the teacher.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting the teacher: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static boolean isTeacherExists(int teacherId) {
        try {
            String query = "SELECT COUNT(*) FROM Teachers WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, teacherId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if teacher exists
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while checking teacher existence: " + e.getMessage());
        }
        return false;
    }





    private static void teacherMenu() throws SQLException {
        while (true) {
            System.out.println("\nTeacher Menu:");
            System.out.println("1. Manage Student");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Exams");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: manageStudents(); break;
                case 2:  manageCourses(); break;
                case 3: manageExams() ; break;
                case 4: System.exit(0); break;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private static void studentMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View All Courses");
            System.out.println("2. Join Courses");
            System.out.println("3. View Grades");
            System.out.println("4. Join Clubs");
            System.out.println("5. View Joined Clubs");
            System.out.println("6. View Your Hostel");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    joinCourse();
                    break;
                case 3:
                    viewGrades();
                    break;
                case 4:
                    joinClubs();
                    break;
                case 5:
                    viewJoinedClubs();
                    break;
                case 6:
                    viewHostel();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    private static void joinCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID to join: ");
        int courseId = scanner.nextInt();

        try {
            System.out.println("Successfully joined the course with ID " + courseId);
        } catch (Exception e) {
            System.out.println("Error while joining the course: " + e.getMessage());
        }
    }
    private static void viewGrades() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Student ID: ");
        int studentId = scanner.nextInt();

        String query = "SELECT exam_id, score FROM ExamResults WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nExam Results:");
            while (rs.next()) {
                System.out.println("Exam ID: " + rs.getInt("exam_id") +
                        ", Score: " + rs.getInt("score"));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching grades: " + e.getMessage());
        }
    }
    private static void joinClubs() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Club ID to join: ");
        int clubId = scanner.nextInt();
        System.out.print("Enter your Student ID: ");
        int studentId = scanner.nextInt();

        String query = "INSERT INTO ClubMemberships (student_id, club_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, clubId);

            stmt.executeUpdate();
            System.out.println("Successfully joined the club.");
        } catch (SQLException e) {
            System.out.println("Error while joining the club: " + e.getMessage());
        }
    }
    private static void viewJoinedClubs() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Student ID: ");
        int studentId = scanner.nextInt();

        String query = "SELECT c.id, c.name FROM Club c " +
                "JOIN ClubMemberships cm ON c.id = cm.club_id " +
                "WHERE cm.student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nJoined Clubs:");
            while (rs.next()) {
                System.out.println("Club ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching joined clubs: " + e.getMessage());
        }
    }
    private static void viewHostel() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Student ID: ");
        int studentId = scanner.nextInt();

        String query = "SELECT h.name FROM Hostels h " +
                "JOIN Students s ON h.id = s.hostel_id " +
                "WHERE s.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Hostel Name: " + rs.getString("name"));
            } else {
                System.out.println("No hostel assigned.");
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching hostel information: " + e.getMessage());
        }
    }

    private static void viewAllTeachers() throws SQLException {
        String query = "SELECT * FROM teachers";

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
            System.out.println(String.format("%-5s %-20s %-5s %-15s %-10s", "ID", "Name", "Age", "Department", "Hostel_ID"));

            System.out.println("----------------------------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("%-5d %-20s %-5d %-15d %-10d",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getInt("department_id"),
                        rs.getInt("hostel_id")));

            }
        }
    }

    private static void manageExams() throws SQLException {
        while (true) {
            System.out.println("\nExam Management Menu:");
            System.out.println("1. Create Exam");
            System.out.println("2. Delete Exam");
            System.out.println("3. Assign Marks to Students");
            System.out.println("4. View All Exams");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createExam();
                    break;
                case 2:
                    deleteExam();
                    break;
                case 3:
                    assignMarksToStudents();
                    break;
                case 4:
                    viewAllExams();
                    break;
                case 5:
                    return;  // Exit to previous menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createExam() throws SQLException {
        System.out.print("Enter Exam Name: ");
        scanner.nextLine();  // Consume newline character
        String examName = scanner.nextLine();

        System.out.print("Enter Course ID for the exam: ");
        int courseId = scanner.nextInt();

        System.out.print("Enter Teacher ID: ");
        int teacherId = scanner.nextInt();

        String query = "INSERT INTO Exams (name, course_id, teacher_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, examName);
            stmt.setInt(2, courseId);
            stmt.setInt(3, teacherId);
            stmt.executeUpdate();
            System.out.println("Exam created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating exam: " + e.getMessage());
        }
    }

    private static void deleteExam() throws SQLException {
        System.out.print("Enter Exam ID to delete: ");
        int examId = scanner.nextInt();

        // Check if the exam exists before trying to delete
        if (!isExamExists(examId)) {
            System.out.println("Exam with ID " + examId + " does not exist.");
            return;
        }

        String query = "DELETE FROM Exams WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Exam deleted successfully.");
            } else {
                System.out.println("Exam not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting exam: " + e.getMessage());
        }
    }

    private static void assignMarksToStudents() throws SQLException {
        System.out.print("Enter Exam ID: ");
        int examId = scanner.nextInt();

        if (!isExamExists(examId)) {
            System.out.println("Exam with ID " + examId + " does not exist.");
            return;
        }

        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();

        // Check if the student exists
        if (!isStudentExists(studentId)) {
            System.out.println("Student with ID " + studentId + " does not exist.");
            return;
        }

        System.out.print("Enter Marks for the student: ");
        int marks = scanner.nextInt();

        String query = "INSERT INTO ExamResults (exam_id, student_id, score) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            stmt.setInt(2, studentId);
            stmt.setInt(3, marks);
            stmt.executeUpdate();
            System.out.println("Marks assigned successfully.");
        } catch (SQLException e) {
            System.out.println("Error assigning marks: " + e.getMessage());
        }
    }

    private static void viewAllExams() throws SQLException {
        String query = "SELECT e.id, e.name, c.name AS course_name, t.name AS teacher_name " +
                "FROM Exams e " +
                "JOIN Courses c ON e.course_id = c.id " +
                "JOIN Teachers t ON e.teacher_id = t.id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nExams List:");
            System.out.println("ID    Name            Course Name    Teacher Name");
            System.out.println("-----------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String courseName = rs.getString("course_name");
                String teacherName = rs.getString("teacher_name");
                System.out.printf("%-6d %-15s %-15s %-15s\n", id, name, courseName, teacherName);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing exams: " + e.getMessage());
        }
    }

    private static boolean isExamExists(int examId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Exams WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, examId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Return true if exam exists, false otherwise
            }
        }
        return false;  // Return false if query fails or exam does not exist
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

        System.out.print("Enter Student Name: ");
        name = scanner.nextLine();

        while (true) {
            System.out.print("Enter Age: ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number for age.");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.print("Enter Department ID: ");
            if (scanner.hasNextInt()) {
                deptId = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number for Department ID.");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.print("Enter Hostel ID: ");
            if (scanner.hasNextInt()) {
                hostelId = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number for Hostel ID.");
                scanner.nextLine();
            }
        }

        if (!isHostelExists(hostelId)) {
            System.out.println("Hostel with ID " + hostelId + " does not exist. Please add the hostel first.");
            return;
        }


        scanner.nextLine();
        System.out.print("Enter Password: ");
        password = scanner.nextLine();

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

    private static boolean isHostelExists(int hostelId) {
        String query = "SELECT COUNT(*) FROM Hostels WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, hostelId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    private static void viewEnrolledStudents() {

    }

    private static void manageCourses() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Course Management:");
            System.out.println("1. Add Course");
            System.out.println("2. Edit Course");
            System.out.println("3. Delete Course");
            System.out.println("4. View All Courses");
            System.out.println("5. Back to Previous Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    editCourse();
                    break;
                case 3:
                    deleteCourse();
                    break;
                case 4:
                    viewAllCourses();
                    break;
                case 5:
                    System.out.println("Returning to Previous Menu.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static boolean isDepartmentValid(int departmentId) throws SQLException {
        String query = "SELECT id FROM departments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    private static void addCourse() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        // Prompt user for department ID
        System.out.print("Enter Department ID: ");
        int departmentId = scanner.nextInt();

        // Validate department ID
        if (!isDepartmentValid(departmentId)) {
            System.out.println("Error: Department ID is invalid.");
            return;
        }

        // SQL query to insert the course into the courses table with department_id
        String query = "INSERT INTO courses (name, code, department_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseName);
            stmt.setString(2, courseCode);
            stmt.setInt(3, departmentId);  // Set the department_id parameter

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Course added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    private static void editCourse() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Course ID to Edit: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter New Course Name: ");
        String newCourseName = scanner.nextLine();

        System.out.print("Enter New Course Code: ");
        String newCourseCode = scanner.nextLine();

        String query = "UPDATE courses SET name = ?, code = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newCourseName);
            stmt.setString(2, newCourseCode);
            stmt.setInt(3, courseId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Course updated successfully!");
            } else {
                System.out.println("No course found with the provided ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating course: " + e.getMessage());
        }
    }

    private static void deleteCourse() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Course ID to Delete: ");
        int courseId = scanner.nextInt();

        String query = "DELETE FROM courses WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("No course found with the provided ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
        }
    }

    private static void viewAllCourses() throws SQLException {
        String query = "SELECT * FROM courses";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("List of Courses:");
            boolean coursesExist = false;
            while (rs.next()) {
                int courseId = rs.getInt("id");
                String courseName = rs.getString("name");
                String courseCode = rs.getString("code");

                System.out.println("Course ID: " + courseId + ", Name: " + courseName + ", Code: " + courseCode);
                coursesExist = true;
            }

            if (!coursesExist) {
                System.out.println("No courses found.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving courses: " + e.getMessage());
        }
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



    private static void manageClubs() {

    }

    private static void viewCourses() {
        String query = "SELECT id, name, code FROM courses";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nAvailable Courses:");
            while (rs.next()) {
                System.out.println("Course ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Code: " + rs.getString("code"));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching courses: " + e.getMessage());
        }
    }



}

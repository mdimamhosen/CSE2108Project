package university;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBUtil {
    public static Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/UniversityDB";
            String username = "root";
            String password = "mdimamhosen";
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new SQLException("Error connecting to the database", e);
        }
    }
}

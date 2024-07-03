import java.sql.*;

public class Model {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ies_math_challenge";
        String username="root";
        String password = "Tribalchief14.";

        return DriverManager.getConnection(url,username,password);
    }
}

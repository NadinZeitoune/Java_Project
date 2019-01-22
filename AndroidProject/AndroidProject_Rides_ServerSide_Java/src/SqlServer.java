import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlServer {

    public static void insert(){
        String userName = "nadin";
        String password = "12345";
        String firstName = "nadin";
        String lastName = "zeitoune";
        String phoneNumber = "0546543455";

        try (Connection conn = getConn()){
            try (PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO users(username, password, first_name, last_name, phone_number) VALUES (?,?,?,?,?)")){
                statement.setString(1, userName);
                statement.setString(2, password);
                statement.setString(3, firstName);
                statement.setString(4, lastName);
                statement.setString(5, phoneNumber);
                int rowsAffected = statement.executeUpdate();
                System.out.println("affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(){
        String userName = "nadin";

        try (Connection conn = getConn()){
            try (PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM users WHERE (username = userName)")){
                //statement.setString(1, userName);
                int rowsAffected = statement.executeUpdate();
                System.out.println("affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConn() throws SQLException{
        String connectionString = "jdbc:mysql://localhost:3306/ride_db?useSSL=false";
        String user = "nadin";
        String password = "NadinSql81196";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(connectionString, user, password);
    }
}

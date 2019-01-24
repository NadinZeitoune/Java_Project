import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class SqlServer {

    public static int insertUser(User user){
        // Get user details.
        String userName = user.getUserName();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = String.valueOf(user.getPhoneNumber());
        int rowsAffected = 0;

        // Connect to mySql table and insert the new user.
        try (Connection conn = getConn()){
            try (PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO users(username, password, first_name, last_name, phone_number) VALUES (?,?,?,?,?)")){
                statement.setString(1, userName);
                statement.setString(2, password);
                statement.setString(3, firstName);
                statement.setString(4, lastName);
                statement.setString(5, phoneNumber);
                rowsAffected = statement.executeUpdate();
            }catch (MySQLIntegrityConstraintViolationException e){
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return rowsAffected;
    }

    public static int searchUserNameForSignUp(String userName){
        // Connect to mySql table.
        try(Connection conn = getConn()){
            // Check if there is already username like param.
            try (PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ?")){
                statement.setString(1, userName);
                try (ResultSet resultSet = statement.executeQuery()){
                    // If there is- send 1.
                    while (resultSet.next()){
                        return 1;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static User searchUsernameAndPasswordForLogin(User user){
        // Connect to mySql table.
        try (Connection conn = getConn()){
            // Check if BOTH username and password are correct.
            try (PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?")){
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getPassword());

                try (ResultSet resultSet = statement.executeQuery()){
                    // If they are - send full user details.
                    while (resultSet.next()){
                        // Create user to return
                        User newUser = new User(resultSet.getString(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
                        return newUser;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Connection getConn() throws SQLException{
        // Get connection to mySql schema.
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
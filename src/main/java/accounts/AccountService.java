package accounts;

import dbService.MyService;
import dbService.dataSets.UsersDataSet;

import java.sql.*;

public class AccountService implements MyService {
    private final Connection connection;

    public AccountService() {
        this.connection = getPostgreSqlConnection();
    }

    public void addNewUser(UsersDataSet userProfile) {
        String insertQuery = "INSERT INTO users(login, pass) VALUES(?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, userProfile.getLogin());
            statement.setString(2, userProfile.getPass());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UsersDataSet getUserByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, login);
            ResultSet results = statement.executeQuery();

            if (!results.first())
                return null;

            String userLogin = results.getString(2);
            String userPass = results.getString(3);
            return new UsersDataSet(userLogin, userPass);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getPostgreSqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());
            String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test";
            String USER = "postgres";
            String PASS = "postgres";

            System.out.println("URL: " + DB_URL + "\n");

            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

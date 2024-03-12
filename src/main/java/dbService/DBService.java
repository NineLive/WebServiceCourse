package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService implements MyService{
    private final Connection connection;

    public DBService() {
        this.connection = getPostgreSqlConnection();
    }

    public UsersDataSet getUserByLogin(String login)  {
        try {
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            return dao.get(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewUser(UsersDataSet user) {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            dao.insertUser(user);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    @SuppressWarnings("UnusedDeclaration")
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

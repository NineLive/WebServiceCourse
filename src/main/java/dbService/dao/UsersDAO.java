package dbService.dao;

import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet get(String login) throws SQLException {
        return executor.execQuery("SELECT * FROM users WHERE login='" + login + "'", result ->
                result.next() ? new UsersDataSet(result.getString(2), result.getString(3)) : null);
    }

    public long getUserId(String login) throws SQLException {
        return executor.execQuery("SELECT * FROM users WHERE login='" + login + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public void insertUser(UsersDataSet user) throws SQLException {
        executor.execUpdate("INSERT INTO users(login, pass) VALUES('" + user.getLogin() + "', '" + user.getPass() + "')");


    }

    public void createTable() throws SQLException {
        executor.execUpdate("CREATE TABLE IF NOT EXISTS users (id BIGSERIAL NOT NULL PRIMARY KEY, login VARCHAR(50) NOT NULL, pass VARCHAR(50) NOT NULL);");
    }


    public void dropTable() throws SQLException {
        executor.execUpdate("DROP TABLE users");
    }
}

package dbService;

import dbService.dataSets.UsersDataSet;

public interface MyService {
    UsersDataSet getUserByLogin(String login);
    void addNewUser(UsersDataSet userProfile);
}

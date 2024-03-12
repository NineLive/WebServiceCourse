package dbService.dataSets;

@SuppressWarnings("UnusedDeclaration")
public class UsersDataSet {

    private String login;
    private String pass;

    public UsersDataSet(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "login=" + login +
                ", pass='" + pass + '\'' +
                '}';
    }
}

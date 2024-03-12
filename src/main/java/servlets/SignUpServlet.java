package servlets;

import accounts.AccountService;
import dbService.MyService;
import dbService.dataSets.UsersDataSet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final MyService accountService;

    public SignUpServlet(MyService accountService) {
        this.accountService = accountService;
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");


        if (login == null || pass == null || login.isEmpty() || pass.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!(accountService.getUserByLogin(login) == null)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("That username’s been taken.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsersDataSet profile = new UsersDataSet(login, pass);
        accountService.addNewUser(profile);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Signup successful");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

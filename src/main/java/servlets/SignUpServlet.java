package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
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

        UserProfile profile = new UserProfile(login, pass);
        accountService.addNewUser(profile);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Signup successful");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

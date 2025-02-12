package am.personal.acc_management.controller;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.Repo.accRepoJDBC;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.util.DBconnectionJDBC;
import am.personal.acc_management.util.Exceptions.InvalidInputException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class changeServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        accService accservice = new accService(new accRepoJDBC(DBconnectionJDBC.getDB().getConn()));

        try {
            if(user != null) {
                accservice.changePassword(user.getEmail(),
                        (String) req.getParameter("new1"),
                        (String) req.getParameter("new2"));
                req.getRequestDispatcher("/AuthOnly/home.jsp").forward(req, resp);
            }
            else
            {
                req.setAttribute("ErrorMessage", "Please register first");
                req.getRequestDispatcher("index.jsp").forward(req,resp);
            }
        } catch (InvalidInputException e) {
            req.setAttribute("ErrorMessage", e.getMessage());
            req.getRequestDispatcher("AuthOnly/changePassword.jsp").forward(req, resp);
        }

    }
}

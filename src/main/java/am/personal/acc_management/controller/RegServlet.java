package am.personal.acc_management.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.Repo.accRepoJDBC;
import am.personal.acc_management.Repo.productRepoJDBC;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.productService;
import am.personal.acc_management.util.DBconnectionJDBC;
import am.personal.acc_management.util.Exceptions.*;


public class RegServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        accService accService = new accService(new accRepoJDBC(DBconnectionJDBC.getDB().getConn()));
        User user = new User(email,username,password,0);
        productService productservice = new productService(new productRepoJDBC(DBconnectionJDBC.getDB().getConn()));

        try{
            accService.addUser(user);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("product", productservice.getAll());
            resp.sendRedirect("AuthOnly/home.jsp");
        }
        catch (InvalidInputException | UserExistsException e)
        {
            req.setAttribute("ErrorMessage", e.getMessage());
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}

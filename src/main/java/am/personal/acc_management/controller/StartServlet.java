package am.personal.acc_management.controller;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Model.User;
import am.personal.acc_management.Repo.accRepoJDBC;
import am.personal.acc_management.Repo.productRepo;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.productService;
import am.personal.acc_management.util.DBconnection;
import am.personal.acc_management.util.Exceptions.InvalidInputException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StartServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String email = null;
        String password = null;
        for (Cookie cookie : cookies)
        {
            if(cookie.getName().equals("CredE"))
                email = cookie.getValue();
            if(cookie.getName().equals("CredP"))
                password = cookie.getValue();
        }

        accService accservice = new accService(new accRepoJDBC(DBconnection.getDB().getConn()));
        productService productservice = new productService(new productRepo(DBconnection.getDB().getConn()));

        if(email != null && password != null) {
            try {
                User user = accservice.getUser(email,password);
                List<Product> products = productservice.getAll();
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("products", products);
                LoginServlet.StoreCredentials(resp, email, password);
                req.getRequestDispatcher("/AuthOnly/home.jsp").forward(req, resp);
            } catch (InvalidInputException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            resp.sendRedirect("index.jsp");
        }

    }
}

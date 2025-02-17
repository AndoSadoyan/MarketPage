package am.personal.acc_management.controller;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Model.User;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.productService;
import am.personal.acc_management.myBeans;

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

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("CredE"))
                    email = cookie.getValue();
                if (cookie.getName().equals("CredP"))
                    password = cookie.getValue();
            }
        }

        accService accservice = myBeans.accServiceBean();
        productService productservice = myBeans.productServiceBean();

        if(email != null && password != null) {
            User user = accservice.getUserByEmail(email);
            if(!user.getPassword().equals(password)) {
                resp.sendRedirect("index.jsp");
                return;
            }
            List<Product> products = productservice.getAll();
            List<Product> cart = user.getCart();
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("products", products);
            req.getSession().setAttribute("cart", cart);
            LoginServlet.StoreCredentials(resp, email, password);
            resp.sendRedirect("/AuthOnly/home.jsp");
        }
        else
        {
            resp.sendRedirect("index.jsp");
        }

    }
}

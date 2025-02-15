package am.personal.acc_management.controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Model.User;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.productService;
import am.personal.acc_management.myBeans;
import am.personal.acc_management.util.Exceptions.InvalidInputException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        accService accservice = myBeans.accServiceBean;
        productService productservice = myBeans.productServiceBean;

        try {
            User user = accservice.getUser(email,password);
            List<Product> products = productservice.getAll();
            List<Product> cart = user.getCart();
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("products", products);
            req.getSession().setAttribute("cart", cart);
            if(req.getParameter("remember") != null)
            {
                StoreCredentials(resp, email, password);
            }
            resp.sendRedirect("AuthOnly/home.jsp");
        } catch (InvalidInputException e) {
            req.setAttribute("ErrorMessage", e.getMessage());
            req.getRequestDispatcher("signin.jsp").forward(req, resp);
        }






    }

    static void StoreCredentials(HttpServletResponse resp, String email, String password) {
        Cookie cookieEmail = new Cookie("CredE",email);
        Cookie cookiePassword = new Cookie("CredP",password);
        cookieEmail.setMaxAge(3600*48);
        cookiePassword.setMaxAge(3600*48);
        resp.addCookie(cookieEmail);
        resp.addCookie(cookiePassword);
    }
}

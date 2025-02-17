package am.personal.acc_management.controller;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.productService;
import am.personal.acc_management.myBeans;
import am.personal.acc_management.util.Exceptions.InvalidInputException;
import am.personal.acc_management.util.Exceptions.NoProductException;
import am.personal.acc_management.util.Exceptions.UserExistsException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class toCartServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productName");
        String email = req.getParameter("email");
        productService productservice = myBeans.productServiceBean();
        accService accservice = myBeans.accServiceBean();

        try {
            var product = productservice.getProduct(productName);
            productservice.removeProduct(productName);
            product.setAmount(1);
            User user = accservice.getUserByEmail(email);
            accservice.addToCart(user,product);
            req.getSession().setAttribute("cart",user.getCart());
            req.getSession().setAttribute("products",productservice.getAll());
            resp.sendRedirect("AuthOnly/home.jsp");
        }
        catch (NoProductException e)
        {

        } catch (InvalidInputException | UserExistsException e) {
            throw new RuntimeException(e);
        }

    }
}

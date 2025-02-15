package am.personal.acc_management.controller;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.Repo.Account.accRepoJDBC;
import am.personal.acc_management.Repo.Account.accRepoJPA;
import am.personal.acc_management.Repo.Product.productRepoJPA;
import am.personal.acc_management.Repo.cartRepoJDBC;
import am.personal.acc_management.Repo.Product.productRepoJDBC;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.cartService;
import am.personal.acc_management.Service.productService;
import am.personal.acc_management.util.DBconnectionJDBC;
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
        productService productservice = new productService(new productRepoJPA());
        accService accservice = new accService(new accRepoJPA());

        try {
            productservice.removeProduct(productName);
            var product = productservice.getProduct(productName);
            product.setAmount(1);
            User user = accservice.getUserByEmail(email);
            user.getCart().add(product);
            accservice.updateUser(user);
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

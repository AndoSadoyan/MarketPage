package am.personal.acc_management.controller;

import am.personal.acc_management.Repo.accRepoJDBC;
import am.personal.acc_management.Repo.cartRepo;
import am.personal.acc_management.Repo.productRepo;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.cartService;
import am.personal.acc_management.Service.productService;
import am.personal.acc_management.util.DBconnection;
import am.personal.acc_management.util.Exceptions.NoProductException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class toCartServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productName");
        String email = (String)req.getParameter("email");
        productService productservice = new productService(new productRepo(DBconnection.getDB().getConn()));
        accService accservice = new accService(new accRepoJDBC(DBconnection.getDB().getConn()));
        cartService cartservice = new cartService(new cartRepo(DBconnection.getDB().getConn()));

        try {
            productservice.removeProduct(productName);
            cartservice.addToCart(email, productName);
            req.getSession().setAttribute("cart",cartservice.getProducts(email));
            req.getSession().setAttribute("products",productservice.getAll());
            resp.sendRedirect("AuthOnly/home.jsp");
        }
        catch (NoProductException e)
        {

        }

    }
}

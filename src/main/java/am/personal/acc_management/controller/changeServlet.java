package am.personal.acc_management.controller;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.Service.accService;
import am.personal.acc_management.myBeans;
import am.personal.acc_management.util.Exceptions.InvalidInputException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class changeServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        accService accservice = myBeans.accServiceBean();

        try {
            if(user != null) {
                accservice.changePassword(user.getEmail(),
                        (String) req.getParameter("new1"),
                        (String) req.getParameter("new2"));
                var cookies = req.getCookies();
                if(Arrays.stream(cookies).anyMatch(c -> c.getName().equals("CredP"))) {
                    Cookie cookie = new Cookie("CredP", req.getParameter("new1"));
                    cookie.setMaxAge(3600 * 24);
                    resp.addCookie(cookie);
                }
                req.getRequestDispatcher("/AuthOnly/home.jsp").forward(req, resp);
            }
            else
            {
                req.setAttribute("ErrorMessage", "Please register first");
                resp.sendRedirect("index.jsp");
            }
        } catch (InvalidInputException e) {
            req.setAttribute("ErrorMessage", e.getMessage());
            req.getRequestDispatcher("AuthOnly/changePassword.jsp").forward(req, resp);
        }

    }
}

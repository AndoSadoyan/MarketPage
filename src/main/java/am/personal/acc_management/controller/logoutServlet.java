package am.personal.acc_management.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class logoutServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var cookies = req.getCookies();
        for (var cookie : cookies)
        {
            if(cookie.getName().equals("CredE") || cookie.getName().equals("CredP")) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }
}

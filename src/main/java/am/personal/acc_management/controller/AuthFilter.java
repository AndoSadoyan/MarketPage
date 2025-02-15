package am.personal.acc_management.controller;

import am.personal.acc_management.Model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute("user");
        if(user == null)
        {
            req.setAttribute("ErrorMessage", "Please register first");
            req.getRequestDispatcher("../index.jsp").forward(req,resp);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

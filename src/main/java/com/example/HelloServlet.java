package com.example;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html");
        resp.getWriter().println("<h1>Hello DevOps! Servlet deployed successfully!</h1>");
    }
}

package jdbc.servlets;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    private static String index = "/WEB-INF/view/index.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        req.getRequestDispatcher(index).forward(req, resp);

                resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();
        pw.println("<B>Hello!");
        pw.close();
    }

//    @Override
//    public void service(ServletRequest req, ServletResponse response) throws ServletException, IOException {
//        super.service(req, response);
//
//        response.setContentType("text/html");
//        PrintWriter pw = response.getWriter();
//        pw.println("<B>Hello!");
//        pw.close();
//    }
}

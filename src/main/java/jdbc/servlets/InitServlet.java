package jdbc.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import static jdbc.config.DataSource.initDataBase;

@WebServlet(urlPatterns = "/")
public class InitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        initDataBase();
    }
}

package co.com.nuevaera.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();

        String thisURL = req.getRequestURI();

        resp.setContentType("text/html");
        if (req.getUserPrincipal() != null) {
            resp.getWriter().println("<p>Hola , " +
                                     req.getUserPrincipal().getName() +
                                     "!esta la URL <a href=\"" +
                                     userService.createLogoutURL(thisURL) +
                                     "\"> de salida</a>.</p>");
        } else {
            resp.getWriter().println("<p>Por favor  <a href=\"" +
                                     userService.createLoginURL(thisURL) +
                                     "\">ingresa</a>.</p>");
        }
    }
}
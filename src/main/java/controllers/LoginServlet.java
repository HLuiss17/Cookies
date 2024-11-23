package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;


@WebServlet({"/login","/login.html"})
public class LoginServlet  extends HttpServlet {
    final  static String USERNAME = "admin";
    final  static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies=req.getCookies() != null ? req.getCookies() : new Cookie[0];

        Optional <String> cookieOptional = Arrays.stream(cookies)
                .filter(c->"username".equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
        if(cookieOptional.isPresent()){
            resp.setContentType("text/html;charset=UTF-8");
            try(PrintWriter out = resp.getWriter()) {
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<title>Hola "+cookieOptional.get()+"</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Login Correcto</h1>");
                out.println("<h3>Hola "+cookieOptional.get()+"ya as uniciado sesion anteriormente</h3>");
                out.println("<p><a href="+req.getContextPath()+"/index.html>Volver al inicio</a></p>");
                out.println("</body>");
                out.println("</html>");
            }

        }else{
            getServletContext().getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(username.equals(USERNAME) && password.equals(PASSWORD)){
            Cookie usernaCookie = new Cookie("username",username);
            resp.addCookie(usernaCookie);
            resp.setContentType("text/html;charset=UTF-8");
            try(PrintWriter out = resp.getWriter()) {
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<title>Login Correcto</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Login Correcto</h1>");
                out.println("<h3>Bienvenido a mi aplicacion "+username+"</h3>");
                out.println("<p><a href="+req.getContextPath()+"/index.html>Volver al inicio</a></p>");
                out.println("</body>");
                out.println("</html>");
                resp.sendRedirect(req.getContextPath()+"/login.html");

            }

        }else{
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Lo sentimos no tienes acceso");
        }
    }
}

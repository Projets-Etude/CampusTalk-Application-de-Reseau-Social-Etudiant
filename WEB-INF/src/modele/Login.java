package modele;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import controler.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")

public class Login extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    
        res.setContentType("text/html");
        UserDAO userDAO = new UserDAO();
        String pseudo  = req.getParameter("pseudo");
        String mdp  = req.getParameter("mdp");

        User user = userDAO.findByIDandPWD(pseudo, mdp);
        HttpSession session = req.getSession(true);
        session.setAttribute("login", user);
        res.sendRedirect("/sae-s4.a02.1/accueil.jsp");;

    }
}

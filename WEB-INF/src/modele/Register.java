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

@WebServlet("/register")

public class Register extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        DS ds = new DS();
        UserDAO userDAO = new UserDAO();
        String pseudo  = req.getParameter("pseudo");
        String prenom  = req.getParameter("prenom");
        String nom  = req.getParameter("nom");
        String anniv  = req.getParameter("anniv");
        String email  = req.getParameter("email");
        String mdp  = req.getParameter("mdp");

        Connection con = ds.getConnection();

        String query = "insert into utilisateur values (?,?,?,?,'"+LocalDate.now()+"',?,null,?)";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, pseudo);
            ps.setString(2, email);
            ps.setString(3, prenom);
            ps.setString(4, nom);
            ps.setDate(5, Date.valueOf(LocalDate.parse(anniv)));
            ps.setString(6, mdp);
            out.println(ps.toString());
            int index = ps.executeUpdate();
            User user = userDAO.findByID(pseudo);
            
            if(index == 1){
                HttpSession session = req.getSession(true);
                session.setAttribute("login", user);
                res.sendRedirect("/sae-s4.a02.1/accueil.jsp");

            }else{              
            }

            con.close();

        }
        catch(SQLException e){
            res.sendRedirect("/sae-s4.a02.1/login.html");
            out.println(e.getLocalizedMessage());
        }
    }
}

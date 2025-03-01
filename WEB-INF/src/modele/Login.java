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
        PrintWriter out = res.getWriter();
        DS ds = new DS();
        UserDAO userDAO = new UserDAO();
        String pseudo  = req.getParameter("pseudo");
        String mdp  = req.getParameter("mdp");

        Connection con = ds.getConnection();

        String query = "select * from utilisateur where UPseudo = ? and Mdp = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, pseudo);
            ps.setString(2, mdp);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                HttpSession session = req.getSession(true);
                User user = userDAO.findByID(pseudo);
                session.setAttribute("login", user);
                res.sendRedirect("/sae-s4.a02.1/accueil.jsp");

            }else{
                res.sendRedirect("/sae-s4.a02.1/login.html"); 
            }
            con.close();

        }
        catch(SQLException e){
            res.sendRedirect("/sae-s4.a02.1/login.html"); 
            System.out.println(e.getLocalizedMessage());
        }
    }
}

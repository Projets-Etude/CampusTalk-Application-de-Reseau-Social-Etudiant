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

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")

public class Login extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        DS ds = new DS();
        String pseudo  = req.getParameter("pseudo");
        String mdp  = req.getParameter("mdp");

        Connection con = ds.getConnection();

        String query = "select * from utilisateur where UPseudo = ? and Mdp = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, pseudo);
            ps.setString(2, mdp);
            out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                res.sendRedirect("/sae-s4.a02.1/accueil.jsp?pseudo=" + pseudo);

            }else{              
            }

            con.close();

        }
        catch(SQLException e){
            out.println(e.getLocalizedMessage());
        }
    }
}

package modele;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/token1")

public class test extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        DS ds = new DS();

        Connection con = ds.getConnection();

        String query = "select * from Utilisateur";
        try{

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                out.println(rs.getString("email"));

            }else{
                out.println("<h1>Vous n'etes pas connu ici !</h1>");
            }

            con.close();

        }
        catch(SQLException e){
            out.println(e.getMessage());
        }
    }
}

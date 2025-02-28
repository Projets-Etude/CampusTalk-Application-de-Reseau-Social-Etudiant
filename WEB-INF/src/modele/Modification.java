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

@WebServlet("/modification")

public class Modification extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    
        res.setContentType("text/html");
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("login");
        System.out.println(user.getEmail());
        PrintWriter out = res.getWriter();
        String pseudo  = req.getParameter("pseudo");
        String prenom  = req.getParameter("prenom");
        String nom  = req.getParameter("nom");
        String anniv  = req.getParameter("anniv");
        String email  = req.getParameter("email");
        String description = req.getParameter("description");
        String mdp  = req.getParameter("mdp");
        UserDAO userDAO = new UserDAO();
        if(pseudo == null || pseudo.isEmpty()){
            pseudo = user.getUPseudo();
        }
        if(prenom == null || prenom.isEmpty()){
            prenom = user.getPrenom();
        }
        if(nom == null || nom.isEmpty()){
            nom = user.getNom();
        }
        if(anniv == null || anniv.isEmpty()){
            anniv = user.getAnniversaire().toString();
        }
        if(email == null || email.isEmpty()){
            email = user.getEmail();
        }
        if(description == null || description.isEmpty()){
            description = user.getDescription();
        }
        if(mdp == null || mdp.isEmpty()){
            mdp = user.getMdp();
        }
        User doneUpdate = userDAO.updateUser(pseudo, email, prenom, nom, anniv, description, mdp, user.getUPseudo());
        if(doneUpdate != null){
            System.out.println("oui");
            session.setAttribute("login", doneUpdate);
            res.sendRedirect("/sae-s4.a02.1/accueil.jsp");
        }else{
            res.sendRedirect("/sae-s4.a02.1/accueil.jsp");
            System.out.println("non");
        }
    }
}

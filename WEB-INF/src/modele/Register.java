package modele;

import java.io.IOException;

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
        UserDAO userDAO = new UserDAO();
        String pseudo  = req.getParameter("pseudo");
        String prenom  = req.getParameter("prenom");
        String nom  = req.getParameter("nom");
        String anniv  = req.getParameter("anniv");
        String email  = req.getParameter("email");
        String mdp  = req.getParameter("mdp");
        User user = userDAO.create(pseudo, email, prenom, nom,anniv, email, mdp);

        if(user != null){
            HttpSession session = req.getSession(true);
            session.setAttribute("login", user);
            res.sendRedirect("/sae-s4.a02.1/accueil.jsp");

        }else{
            res.sendRedirect("/sae-s4.a02.1/accueil.jsp");
        }
        
    }
}

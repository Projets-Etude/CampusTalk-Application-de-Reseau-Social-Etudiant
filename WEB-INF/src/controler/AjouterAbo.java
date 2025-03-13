package controler;

import modele.AbonnementDAO;
import modele.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/suivreUtilisateur")
public class AjouterAbo extends HttpServlet {
    @SuppressWarnings("unused")
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String user1 = req.getParameter("utilisateur");
        UserDAO userDAO = new UserDAO();
        HttpSession session = req.getSession(true);
        User currentUser = (User ) session.getAttribute("login");
        if(currentUser == null){
            res.sendRedirect("login.html");
        }
        User user = userDAO.findByID(user1);
        if(user1 != null){
            AbonnementDAO aboDAO = new AbonnementDAO();
            String abonnementID = UUID.randomUUID().toString();
            Abonnement abonnement = new Abonnement(abonnementID, currentUser.getUPseudo(), user1, LocalDate.now());
            aboDAO.create(abonnement);
            res.sendRedirect("accueil.jsp");
        }
        
    
          
    }
}

package controler;

import modele.AbonnementDAO;
import modele.ConvDAO;
import modele.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/supprimerAbonne")
public class SupprimeAbo extends HttpServlet {
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

        AbonnementDAO aboDAO = new AbonnementDAO();
        ConvDAO convDAO = new ConvDAO();
        convDAO.deleteDuoConversation(user1, currentUser.getUPseudo());
        Abonnement abonnement = aboDAO.findByUsers(currentUser.getUPseudo(), user.getUPseudo());

        aboDAO.deleteByID(abonnement.getAbonnementID());
        res.sendRedirect("accueil.jsp");
        
    
          
    }
}

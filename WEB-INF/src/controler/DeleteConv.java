package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.ConvDAO;
import modele.ParticipationDAO;

import java.io.IOException;

@WebServlet("/deleteConv")
public class DeleteConv extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        HttpSession session = req.getSession(true);

        if (session.getAttribute("login") == null) {
            res.sendRedirect("/sae-s4.a02.1/login.html");
            return;
        }

        User user = (User) session.getAttribute("login");
        String convId = req.getParameter("convId");

        if (convId == null || convId.trim().isEmpty()) {
            res.getWriter().println("Erreur : ID de conversation invalide.");
            return;
        }

        ConvDAO convDAO = new ConvDAO();
        ParticipationDAO participationDAO = new ParticipationDAO();

        if (!convDAO.findByID(convId).getOwner().equals(user.getUPseudo())) {
            res.getWriter().println("Erreur : Vous n'avez pas l'autorisation de supprimer cette conversation.");
            return;
        }

        participationDAO.removeAllParticipants(convId);

        convDAO.deleteById(convId);

        res.sendRedirect("/sae-s4.a02.1/accueil.jsp");
    }
}

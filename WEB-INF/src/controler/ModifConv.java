package controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import modele.*;

@WebServlet("/ModifConv")
public class ModifConv extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        HttpSession session = req.getSession(true);

        if (session.getAttribute("login") == null) {
            res.sendRedirect("/sae-s4.a02.1/login.html");
            return;
        }

        User user = (User) session.getAttribute("login");
        String convId = req.getParameter("convId");
        String newTitle = req.getParameter("nomConv");
        Boolean confidentiality = Boolean.valueOf(req.getParameter("privee"));
        String[] selectedParticipants = req.getParameterValues("participants");

        ConvDAO convDAO = new ConvDAO();
        ParticipationDAO participationDAO = new ParticipationDAO();

        Conv conversation = convDAO.findByID(convId);
        if (conversation == null) {
            res.getWriter().println("Erreur : Conversation introuvable.");
            return;
        }

        if (newTitle == null || newTitle.trim().isEmpty()) {
            newTitle = conversation.getTitre();
        }

        convDAO.updateConversation(convId, newTitle, confidentiality);

        List<User> currentParticipants = participationDAO.findParticipantsByConversationId(convId);
        List<String> currentParticipantUsernames = new ArrayList<>();
        for (User u : currentParticipants) {
            if (!u.getUPseudo().equals(user.getUPseudo())) {
                currentParticipantUsernames.add(u.getUPseudo());
            }
        }

        List<String> newParticipantUsernames = new ArrayList<>();
        if (selectedParticipants != null) {
            for (String pseudo : selectedParticipants) {
                if (!pseudo.equals(user.getUPseudo())) {
                    newParticipantUsernames.add(pseudo);
                }
            }
        }

        List<String> toAdd = new ArrayList<>(newParticipantUsernames);
        toAdd.removeAll(currentParticipantUsernames);

        List<String> toRemove = new ArrayList<>(currentParticipantUsernames);
        toRemove.removeAll(newParticipantUsernames);

        participationDAO.addParticipants(convId, toAdd);

        participationDAO.removeParticipants(convId, toRemove);

        res.sendRedirect("/sae-s4.a02.1/conversation.jsp?id=" + convId);
    }
}

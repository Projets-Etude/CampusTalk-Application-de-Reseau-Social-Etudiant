package controler;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import modele.MessageDAO;
import modele.ParticipationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@WebServlet("/createMessage")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class CreateMessage extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String contenue = req.getParameter("contenu");
        HttpSession session = req.getSession(true);
        ParticipationDAO participationDAO = new ParticipationDAO();
        User auteur = (User) session.getAttribute("login");
        if(auteur == null){
            res.sendRedirect("login.html");
        }
        String cid = req.getParameter("cid");
        if (contenue == null && contenue.trim().isEmpty() && auteur == null && cid == null) {
            res.sendRedirect("conversation.jsp?id=" + cid);
            return;
        }

        Part filePart = req.getPart("image");
        byte[] imageBytes = null;
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                imageBytes = inputStream.readAllBytes();
            }
        }

        if ((contenue == null || contenue.trim().isEmpty()) && (imageBytes == null || imageBytes.length == 0)) {
            res.sendRedirect("conversation.jsp?id=" + cid);
            return;
        }

        String mno = UUID.randomUUID().toString();
        List<User> userList = participationDAO.findParticipantsByConversationId(cid);
        if(!userList.contains(auteur) || (userList.isEmpty() || userList == null)){
            List<String> participant = new ArrayList<>();
            participant.add(auteur.getUPseudo());
            participationDAO.addParticipants(cid, participant);
        }
        Message message = new Message(mno, contenue, LocalDate.now(), auteur.getUPseudo(), imageBytes);

        MessageDAO messageDAO = new MessageDAO();
        messageDAO.create(message, cid);
        res.sendRedirect("conversation.jsp?id=" + cid);
    }
}

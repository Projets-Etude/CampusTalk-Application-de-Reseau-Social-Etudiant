package controler;

import modele.MessageDAO;
import controler.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/createMessage")
public class CreateMessage extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String contenue = req.getParameter("contenu");
        HttpSession session = req.getSession(true);
        User auteur = (User) session.getAttribute("login");
        if(auteur == null){
            res.sendRedirect("login.html");
        }
        String cid = req.getParameter("cid");
        if (contenue == null || contenue.trim().isEmpty() || auteur == null || cid == null) {
            res.sendRedirect("conversation.jsp?id=" + cid);
            return;
        }

        String mno = UUID.randomUUID().toString();
        Message message = new Message(mno, contenue, LocalDate.now(), auteur.getUPseudo());

        MessageDAO messageDAO = new MessageDAO();
        messageDAO.create(message, cid);
        res.sendRedirect("conversation.jsp?id=" + cid);
    }
}

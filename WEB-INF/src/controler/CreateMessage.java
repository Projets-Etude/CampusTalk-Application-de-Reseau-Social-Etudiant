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
        String cid = req.getParameter("cid");
        System.out.println(contenue);
        System.out.println(contenue.trim().isEmpty());
        System.out.println(auteur.getUPseudo());
        System.out.println(cid);
        if (contenue == null || contenue.trim().isEmpty() || auteur == null || cid == null) {
            res.sendRedirect("conversation.jsp?id=" + cid);
            return;
        }
        System.out.println("oui1");

        String mno = UUID.randomUUID().toString();
        Message message = new Message(mno, contenue, LocalDate.now(), auteur.getUPseudo());
        System.out.println("oui2");

        MessageDAO messageDAO = new MessageDAO();
        System.out.println("oui3");
        messageDAO.create(message, cid);
        System.out.println("oui4");
        res.sendRedirect("conversation.jsp?id=" + cid);
    }
}

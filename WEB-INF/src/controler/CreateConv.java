package controler;

import modele.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/createConversation")
public class CreateConv extends HttpServlet {
    @SuppressWarnings("unused")
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ConvDAO convDAO = new ConvDAO();
        String user1 = req.getParameter("utilisateur");
        UserDAO userDAO = new UserDAO();
        HttpSession session = req.getSession(true);
        User currentUser = (User ) session.getAttribute("login");
        if(currentUser == null){
            res.sendRedirect("login.html");
        }
        String existConversationId = convDAO.findDuoConversationID(user1, currentUser.getUPseudo());
        if(existConversationId != null){
            res.sendRedirect("conversation.jsp?id=" + existConversationId);
        }else{
            String cno = UUID.randomUUID().toString();
            Conv conv = new Conv(cno, "Conversation de " + user1 + " et de " + currentUser.getPrenom(), true, true, currentUser.getUPseudo(), LocalDate.now());
            
            convDAO.createDuoConversation(conv,currentUser.getUPseudo(), user1);
            res.sendRedirect("conversation.jsp?id=" + cno);
        }
    }
}

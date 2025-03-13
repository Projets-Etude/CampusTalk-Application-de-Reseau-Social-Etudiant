package controler;

import modele.*;
import controler.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/createConversation")
public class CreateConv extends HttpServlet {
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

        String cno = UUID.randomUUID().toString();
        Conv conv = new Conv(cno, "Conversation de " + user1 + " et de " + currentUser.getPrenom(), true, true, currentUser.getUPseudo(), LocalDate.now());

        ConvDAO convDAO = new ConvDAO();
        convDAO.create(conv);
        res.sendRedirect("conversation.jsp?id=" + cno);



    }
}

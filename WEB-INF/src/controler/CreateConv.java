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
import java.util.List;
import java.util.UUID;
import java.util.Arrays;

@WebServlet("/createConversation")
public class CreateConv extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ConvDAO convDAO = new ConvDAO();
        HttpSession session = req.getSession(true);
        User currentUser = (User) session.getAttribute("login");

        if (currentUser == null) {
            res.sendRedirect("login.html");
            return;
        }

        String convName = req.getParameter("conv-name");

        boolean duo = req.getParameter("duo") != null;

        String[] usersArray = req.getParameterValues("abonnes");

        boolean isPrivate = req.getParameter("conv-private") != null;

        List<String> usersList = Arrays.asList(usersArray);

        if (duo) {
            String user1 = usersList.get(0);
            String existingConvID = convDAO.findDuoConversationID(user1, currentUser.getUPseudo());

            if (existingConvID != null) {
                res.sendRedirect("conversation.jsp?id=" + existingConvID);
                return;
            }

            String convID = UUID.randomUUID().toString();
            Conv conv = new Conv(convID, "Conversation de " + currentUser.getPrenom() + " et " + user1, isPrivate, true, currentUser.getUPseudo(), LocalDate.now());

            System.out.println("test");
            convDAO.createDuoConversation(conv, currentUser.getUPseudo(), user1);

            res.sendRedirect("conversation.jsp?id=" + convID);
        } else {
            String convID = UUID.randomUUID().toString();
            Conv conv = new Conv(convID, convName, isPrivate, false, currentUser.getUPseudo(), LocalDate.now());
            convDAO.createGroupConversation(conv, currentUser.getUPseudo(), usersList);
            res.sendRedirect("conversation.jsp?id=" + convID);
        }
    }
}

package controler;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.ConvDAO;
import modele.ParticipationDAO;


@WebServlet("/conversations/*")
public class ConversationsRestAPI extends HttpServlet{
    ConvDAO dao = new ConvDAO();
    ParticipationDAO participationDAO = new ParticipationDAO();

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(true);
        User currentUser = (User) session.getAttribute("login");

        if (currentUser == null) {
            res.sendRedirect("/sae-s4.a02.1/login.html");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String info = req.getPathInfo();

    
        if (info == null || info.equals("/")) {
            Collection<Conv> l = participationDAO.findConversationsByUser(currentUser.getUPseudo());
            String jsonstring = objectMapper.writeValueAsString(l);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];
        Collection<Message> e = dao.findMessagesByConversationId(id);

        if (e==null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        out.print(objectMapper.writeValueAsString(e));
        return;
    }
}

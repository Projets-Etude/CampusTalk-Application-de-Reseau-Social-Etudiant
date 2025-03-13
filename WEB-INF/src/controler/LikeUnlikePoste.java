package controler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.LikePosteDAO;
import java.io.IOException;

@WebServlet("/LikePosteServlet")
public class LikeUnlikePoste extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("login");
        String userPseudo = user.getUPseudo();


        String posteID = req.getParameter("posteID");

        LikePosteDAO likePosteDAO = new LikePosteDAO();

        if (likePosteDAO.hasLiked(posteID, userPseudo)) {
            likePosteDAO.removeLike(posteID, userPseudo);
        } else {
            likePosteDAO.addLike(posteID, userPseudo);
        }


        String referer = req.getHeader("Referer");
        res.sendRedirect(referer != null ? referer : "accueil.jsp");
    }
}

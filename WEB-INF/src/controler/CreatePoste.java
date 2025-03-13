package controler;

import modele.PosteDAO;
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

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.CharSequenceTranslator;

@WebServlet("/createPoste")
public class CreatePoste extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String contenue = req.getParameter("contenu");
        String isPrive = req.getParameter("prive");
        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        
        contenue = cst.translate(contenue);

        if(isPrive == null){
            isPrive = "null";
        }

        boolean isPrivate = false;
        if(isPrive.equals("on")){
            isPrivate = true;
        }

        HttpSession session = req.getSession(true);
        User auteur = (User) session.getAttribute("login");
        PosteDAO posteDAO = new PosteDAO();
        if(auteur == null){
            res.sendRedirect("login.html");
        }else{

            if (contenue == null || contenue.trim().isEmpty() || auteur == null) {
                res.sendRedirect("accueil.jsp");
                return;
            }

            String posteID = UUID.randomUUID().toString();
            Poste p = new Poste(posteID,contenue,Date.valueOf(LocalDate.now()),isPrivate,auteur.getUPseudo());
            if(p != null){
                posteDAO.create(p);
                res.sendRedirect("accueil.jsp");
            }
    }
          
    }
}

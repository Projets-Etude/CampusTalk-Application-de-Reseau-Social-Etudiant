package modele;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import org.apache.commons.text.*;
import org.apache.commons.text.translate.CharSequenceTranslator;

import controler.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/modification")
@MultipartConfig(maxFileSize = 16177215)
public class Modification extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");
        HttpSession session = req.getSession(true);
        if(session.getAttribute("login") == null){
            res.sendRedirect("/sae-s4.a02.1/login.html");
        }else{


        User user = (User) session.getAttribute("login");
        String pseudo  = req.getParameter("pseudo");
        String prenom  = req.getParameter("prenom");
        String nom  = req.getParameter("nom");
        String anniv  = req.getParameter("anniv");
        String email  = req.getParameter("email");
        String description = req.getParameter("description");
        String mdp  = req.getParameter("mdp");

        Part filePart = req.getPart("photo");
        byte[] photo = null;

        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                photo = inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UserDAO userDAO = new UserDAO();

        CharSequenceTranslator cst = StringEscapeUtils.ESCAPE_HTML4;
        pseudo = cst.translate(pseudo);
        prenom = cst.translate(prenom);
        nom = cst.translate(nom);
        email = cst.translate(email);
        description = cst.translate(description);
        mdp = cst.translate(mdp);

        String ancienPseudo = "";
        if(user != null){
            ancienPseudo = user.getUPseudo();
        }
        if(pseudo == null || pseudo.isEmpty()){
            pseudo = user.getUPseudo();
        }
        if(prenom == null || prenom.isEmpty()){
            prenom = user.getPrenom();
        }
        if(nom == null || nom.isEmpty()){
            nom = user.getNom();
        }
        if(anniv == null || anniv.isEmpty()){
            anniv = user.getAnniversaire().toString();
        }
        if(email == null || email.isEmpty()){
            email = user.getEmail();
        }
        if(description == null || description.isEmpty()){
            description = user.getDescription();
        }
        if(mdp == null || mdp.isEmpty()){
            mdp = user.getMdp();
        }
        if(photo == null){
            photo = user.getPhoto();
        }
        if (req.getContentType() == null || !req.getContentType().startsWith("multipart/form-data")) {
            res.getWriter().println("Erreur : Le formulaire n'est pas envoyé en multipart/form-data");
            return;
        }

        User doneUpdate = userDAO.updateUser(pseudo, email, prenom, nom, anniv, description, mdp, photo, ancienPseudo);
        if(doneUpdate != null){
            session.setAttribute("login", doneUpdate);
            res.sendRedirect("/sae-s4.a02.1/accueil.jsp");
        }else{
            res.sendRedirect("/sae-s4.a02.1/accueil.jsp");
        }
    }
    }
}

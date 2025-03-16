package controler;

import java.util.Base64;
import java.util.Date;

import modele.LikePosteDAO;

import modele.UserDAO;

public class Poste {
    private String posteID;
    private String contenu;
    private Date datePoste;
    private boolean visibiliteRestreinte;
    private String uPseudo;

    public Poste(String posteID, String contenu, Date datePoste, boolean visibiliteRestreinte, String uPseudo) {
        this.posteID = posteID;
        this.contenu = contenu;
        this.datePoste = datePoste;
        this.visibiliteRestreinte = visibiliteRestreinte;
        this.uPseudo = uPseudo;
    }

    public String getPosteID() {
        return posteID;
    }

    public String getContenu() {
        return contenu;
    }

    public Date getDatePoste() {
        return datePoste;
    }

    public boolean isVisibiliteRestreinte() {
        return visibiliteRestreinte;
    }

    public String getUPseudo() {
        return uPseudo;
    }

    public void setPosteID(String posteID) {
        this.posteID = posteID;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDatePoste(Date datePoste) {
        this.datePoste = datePoste;
    }

    public void setVisibiliteRestreinte(boolean visibiliteRestreinte) {
        this.visibiliteRestreinte = visibiliteRestreinte;
    }

    public void setUPseudo(String uPseudo) {
        this.uPseudo = uPseudo;
    }

    public String getPosteSuivi(String userPseudo, LikePosteDAO likePosteDAO){
        boolean hasLike = likePosteDAO.hasLiked(this.getPosteID(), userPseudo);

        String photoPath;
        UserDAO uDAO = new UserDAO();
        User curUser = uDAO.findByID(userPseudo);
        if (curUser.getPhoto() != null) {
            photoPath = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(curUser.getPhoto());
        } else {
            photoPath = "res/avatar.png";
        }

        return "<div class='tweet'>" +
                "<div class='tweet-header'>" +
                "<img class='tweet-profil' src=" + photoPath + " alt=" + userPseudo + " Avatar>" +
                "<div class='tweet-info>'" +
                "<strong>" + this.getUPseudo() + "</strong>" +
                "<div class='tweet-date'>" + this.getDatePoste() + "</div>" +
                "</div>" +
                "</div>" +
        "<div class='tweet-content'>" + this.getContenu() + "</div>" +
        (this.isVisibiliteRestreinte() ? "<div class='tweet-private'>🔒 Privé</div>" : "") +
        "<div class='tweet-footer'>" +
            "<button class='followed-button'>Suivi</button>" +

            "<form action='LikePosteServlet' method='post' class='like-form'>" +
                "<input type='hidden' name='posteID' value='" + this.getPosteID() + "'>" +
                "<button type='submit' class='like-button " + ( hasLike ? "liked" : "") + "'>" +
                    (hasLike ? "💔 Unlike" : "❤️ Like") +
                "</button>" +
            "</form>" +
        "</div>" +
        "</div>";
    }

    public String getPosteCurrentUser(String userPseudo, LikePosteDAO likePosteDAO){
        boolean hasLike = likePosteDAO.hasLiked(this.getPosteID(), userPseudo);

        String photoPath;
        UserDAO uDAO = new UserDAO();
        User curUser = uDAO.findByID(userPseudo);
        if (curUser.getPhoto() != null) {
            photoPath = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(curUser.getPhoto());
        } else {
            photoPath = "res/avatar.png";
        }

        return "<div class='tweet'>" +
                "<div class='tweet-header'>" +
                "<img class='tweet-profil' src=" + photoPath + " alt=" + userPseudo + " Avatar>" +
                "<div class='tweet-info>'" +
                "<strong>" + this.getUPseudo() + "</strong>" +
                "<div class='tweet-date'>" + this.getDatePoste() + "</div>" +
                "</div>" +
                "</div>" +
                "<div class='tweet-content'>" + this.getContenu() + "</div>" +
                (this.isVisibiliteRestreinte() ? "<div class='tweet-private'>🔒 Privé</div>" : "") +
                "<div class='tweet-footer'>" +

                    "<form action='LikePosteServlet' method='post' class='like-form'>" +
                        "<input type='hidden' name='posteID' value='" + this.getPosteID() + "'>" +
                        "<button type='submit' class='like-button " + (hasLike ? "liked" : "") + "'>" +
                            (hasLike ? "💔 Unlike" : "❤️ Like") +
                        "</button>" +
                    "</form>" +
                "</div>" +
                "</div>";
    }

    public String getPosteSuivre(String userPseudo, LikePosteDAO likePosteDAO){
        boolean hasLike = likePosteDAO.hasLiked(this.getPosteID(), userPseudo);

        String photoPath;
        UserDAO uDAO = new UserDAO();
        User curUser = uDAO.findByID(userPseudo);
        if (curUser.getPhoto() != null) {
            photoPath = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(curUser.getPhoto());
        } else {
            photoPath = "res/avatar.png";
        }

        return "<div class='tweet'>" +
                "<div class='tweet-header'>" +
                "<img class='tweet-profil' src=" + photoPath + " alt=" + userPseudo + " Avatar>" +
                "<div class='tweet-info>'" +
                "<strong>" + this.getUPseudo() + "</strong>" +
                "<div class='tweet-date'>" + this.getDatePoste() + "</div>" +
                "</div>" +
                "</div>" +
                "<div class='tweet-content'>" + this.getContenu() + "</div>" +
                (this.isVisibiliteRestreinte() ? "<div class='tweet-private'>🔒 Privé</div>" : "") +
                "<div class='tweet-footer'>" +
                    "<button class='followed-button'>Suivi</button>" +

                    "<form action='LikePosteServlet' method='post' class='like-form'>" +
                        "<input type='hidden' name='posteID' value='" + this.getPosteID() + "'>" +
                        "<button type='submit' class='like-button " + (hasLike ? "liked" : "") + "'>" +
                            (hasLike ? "💔 Unlike" : "❤️ Like") +
                        "</button>" +
                    "</form>" +
                "</div>" +
                "</div>";
    }

    public String getPosteSuivreElse(String userPseudo, LikePosteDAO likePosteDAO){
        boolean hasLike = likePosteDAO.hasLiked(this.getPosteID(), userPseudo);

        String photoPath;
        UserDAO uDAO = new UserDAO();
        User curUser = uDAO.findByID(userPseudo);
        if (curUser.getPhoto() != null) {
            photoPath = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(curUser.getPhoto());
        } else {
            photoPath = "res/avatar.png";
        }

        return "<div class='tweet'>" +
                "<div class='tweet-header'>" +
                "<img class='tweet-profil' src=" + photoPath + " alt=" + userPseudo + " Avatar>" +
                "<div class='tweet-info>'" +
                "<strong>" + this.getUPseudo() + "</strong>" +
                "<div class='tweet-date'>" + this.getDatePoste() + "</div>" +
                "</div>" +
                "</div>" +
                "<div class='tweet-content'>" + this.getContenu()+ "</div>" +
                (this.isVisibiliteRestreinte() ? "<div class='tweet-private'>🔒 Privé</div>" : "") +
                "<div class='tweet-footer'>" +
                    "<a href='suivreUtilisateur?utilisateur=" + this.getUPseudo() + "' class='follow-button'>Suivre</a>" +

                    "<form action='LikePosteServlet' method='post' class='like-form'>" +
                        "<input type='hidden' name='posteID' value='" + this.getPosteID() + "'>" +
                        "<button type='submit' class='like-button " + (hasLike ? "liked" : "") + "'>" +
                            (hasLike ? "💔 Unlike" : "❤️ Like") +
                        "</button>" +
                    "</form>" +
                "</div>" +
                "</div>";
    }
    

}


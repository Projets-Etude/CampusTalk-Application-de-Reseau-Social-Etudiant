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

    public String getPosteHTML(String userPseudo, LikePosteDAO likePosteDAO, boolean isFollowing, boolean isCurrentUser, boolean canFollow) {
        boolean hasLike = likePosteDAO.hasLiked(this.getPosteID(), userPseudo);
        String photoPath = ImageUtils.getProfileImagePath(new UserDAO().findByID(this.uPseudo));

        StringBuilder html = new StringBuilder();
        html.append("<div class='tweet'>")
                .append("<div class='tweet-header'>")
                .append("<img class='tweet-profil' src='").append(photoPath).append("' alt='").append(this.getUPseudo()).append(" Avatar'>")
                .append("<div class='tweet-info'>")
                .append("<strong>").append(this.getUPseudo()).append("</strong>")
                .append("<div class='tweet-date'>").append(this.getDatePoste()).append("</div>")
                .append("</div>")
                .append("</div>")
                .append("<div class='tweet-content'>").append(this.getContenu()).append("</div>");

        if (this.isVisibiliteRestreinte()) {
            html.append("<div class='tweet-private'>🔒 Privé</div>");
        }

        html.append("<div class='tweet-footer'>");

        if (isFollowing) {
            html.append("<button class='followed-button'>Suivi</button>");
        } else if (canFollow) {
            html.append("<a href='suivreUtilisateur?utilisateur=").append(this.getUPseudo()).append("' class='follow-button'>Suivre</a>");
        }

        html.append("<form action='LikePosteServlet' method='post' class='like-form'>")
                .append("<input type='hidden' name='posteID' value='").append(this.getPosteID()).append("'>")
                .append("<button type='submit' class='like-button ").append(hasLike ? "liked" : "").append("'>")
                .append(hasLike ? "❤️" : "🤍").append(" " + likePosteDAO.countLikes(this.getPosteID()))
                .append("</button>")
                .append("</form>")
                .append("</div>")
                .append("</div>");

        return html.toString();
    }
}


package controler;

import java.util.Date;

public class Poste {
    private String posteID;
    private String contenu;
    private Date datePoste;
    private boolean visibiliteRestreinte;
    private String uPseudo;

    // Constructeur
    public Poste(String posteID, String contenu, Date datePoste, boolean visibiliteRestreinte, String uPseudo) {
        this.posteID = posteID;
        this.contenu = contenu;
        this.datePoste = datePoste;
        this.visibiliteRestreinte = visibiliteRestreinte;
        this.uPseudo = uPseudo;
    }

    // Getters
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

    // Setters
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

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "<div class='tweet'>" +
            "<div class='tweet-header'>" +
                "<strong>" + uPseudo + "</strong> · <span class='tweet-date'>" + datePoste + "</span>" +
            "</div>" +
            "<div class='tweet-content'>" + contenu + "</div>" +
            (visibiliteRestreinte ? "<div class='tweet-private'>🔒 Privé</div>" : "") +
            "</div>";
    }

}


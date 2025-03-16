package controler;

import java.time.LocalDate;
import java.util.Base64;

import modele.UserDAO;

public class Abonnement {
    private String abonnementID;
    private String utilisateur1;
    private String utilisateur2;
    private LocalDate dateAbonnement;

    public Abonnement(String abonnementID, String utilisateur1, String utilisateur2, LocalDate dateAbonnement) {
        this.abonnementID = abonnementID;
        this.utilisateur1 = utilisateur1;
        this.utilisateur2 = utilisateur2;
        this.dateAbonnement = dateAbonnement;
    }

    public String getAbonnementID() {
        return abonnementID;
    }

    public void setAbonnementID(String abonnementID) {
        this.abonnementID = abonnementID;
    }

    public String getUtilisateur1() {
        return utilisateur1;
    }

    public void setUtilisateur1(String utilisateur1) {
        this.utilisateur1 = utilisateur1;
    }

    public String getUtilisateur2() {
        return utilisateur2;
    }

    public void setUtilisateur2(String utilisateur2) {
        this.utilisateur2 = utilisateur2;
    }

    public LocalDate getDateAbonnement() {
        return dateAbonnement;
    }

    public void setDateAbonnement(LocalDate dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
    }


    public String toString(String currentUser) {
        UserDAO userDAO = new UserDAO();
        User user1 = userDAO.findByID(utilisateur1);
        User user2 = userDAO.findByID(utilisateur2);

        User targetUser = currentUser.equals(utilisateur1) ? user2 : user1;

        String photoPath;
        if (targetUser.getPhoto() != null) {
            photoPath = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(targetUser.getPhoto());
        } else {
            photoPath = "res/avatar.png";
        }

        return "<div class=\"friend-profile\">" +
                "<div class=\"friend-avatar\">" +
                "<img src=\"" + photoPath + "\" alt=\"" + targetUser.getUPseudo() + " Avatar\">" +
                "</div>" +
                "<div class=\"friend-info\">" +
                "<p><strong>Pseudo :</strong> " + targetUser.getUPseudo() + "</p>" +
                "<p><strong>Prénom :</strong> " + targetUser.getPrenom() + "</p>" +
                "</div>" +
                "<div class=\"friend-remove\">" +
                "<a href=\"/sae-s4.a02.1/createConversation?abonnes=" + targetUser.getUPseudo() + "&duo=true\" class=\"create-conv-btn\">Conversation</a>" +
                "<a href=\"/sae-s4.a02.1/supprimerAbonne?utilisateur=" + targetUser.getUPseudo() + "\" class=\"remove-btn\">&#10005;</a>" +
                "</div>" +
                "</div>";
    }
    
    
}

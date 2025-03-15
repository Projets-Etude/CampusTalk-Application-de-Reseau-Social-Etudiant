package controler;

import java.time.LocalDate;
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
        if(currentUser.equals(utilisateur1)){
            return "<div class=\"friend-profile\">" +
               "<div class=\"friend-avatar\">" +
                   "<img src=\"res\\avatar.png\"" + utilisateur2 + ".jpg\" alt=\"" + utilisateur2 + " Avatar\">" +
               "</div>" +
               "<div class=\"friend-info\">" +
                   "<p><strong>Pseudo :</strong> " + user2.getUPseudo() + "</p>" +
                   "<p><strong>Prénom :</strong> " + user2.getPrenom() + "</p>" +
               "</div>" +
               "<div class=\"friend-remove\">" + 
                    "<a href=\"/sae-s4.a02.1/createConversation?abonnes=" + user2.getUPseudo() + "&duo=true\" class=\"create-conv-btn\">Conversation</a>" +
                   "<a href=\"/sae-s4.a02.1/supprimerAbonne?utilisateur=" + user2.getUPseudo() + "\" class=\"remove-btn\">&#10005;</a>" +
               "</div>" +
           "</div>";

        }else{
            return "<div class=\"friend-profile\">" +
               "<div class=\"friend-avatar\">" +
                   "<img src=\"res\\avatar.png\"" + utilisateur1 + ".jpg\" alt=\"" + utilisateur1 + " Avatar\">" +
               "</div>" +
               "<div class=\"friend-info\">" +
                   "<p><strong>Pseudo :</strong> " + user1.getUPseudo() + "</p>" +
                   "<p><strong>Prénom :</strong> " + user1.getPrenom() + "</p>" +
               "</div>" +
               "<div class=\"friend-remove\">" +
                "<a href=\"/sae-s4.a02.1/createConversation?abonnes=" + user1.getUPseudo() + "&duo=true\" class=\"create-conv-btn\">Conversation</a>"+
                   "<a href=\"/sae-s4.a02.1/supprimerAbonne?utilisateur=" + user1.getUPseudo() + "\" class=\"remove-btn\">&#10005;</a>" +
               "</div>" +
           "</div>";
        }
        
    }
    
    
}

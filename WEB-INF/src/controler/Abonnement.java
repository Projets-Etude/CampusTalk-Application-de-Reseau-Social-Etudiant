package controler;

import java.time.LocalDate;

public class Abonnement {
    private String abonnementID;
    private String utilisateur1;
    private String utilisateur2;
    private LocalDate dateAbonnement;

    // Constructeur
    public Abonnement(String abonnementID, String utilisateur1, String utilisateur2, LocalDate dateAbonnement) {
        this.abonnementID = abonnementID;
        this.utilisateur1 = utilisateur1;
        this.utilisateur2 = utilisateur2;
        this.dateAbonnement = dateAbonnement;
    }

    // Getters et Setters
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
        if(currentUser.equals(utilisateur1)){
            return "<div class=\"friend-profile\">" +
                   "<div class=\"friend-avatar\">" +
                       "<img src=\"res\\avatar.png\"" + utilisateur2 + ".jpg\" alt=\"" + utilisateur2 + " Avatar\">" +
                   "</div>" +
                   "<div class=\"friend-info\">" +
                       "<p><strong>Pseudo :</strong> " + utilisateur2 + "</p>" +
                       "<p><strong>Nom :</strong> " + utilisateur2 + "</p>" +
                   "</div>" +
               "</div>";

        }else{
            return "<div class=\"friend-profile\">" +
                   "<div class=\"friend-avatar\">" +
                       "<img src=\"res\\avatar.png\"" + utilisateur1 + ".jpg\" alt=\"" + utilisateur1 + " Avatar\">" +
                   "</div>" +
                   "<div class=\"friend-info\">" +
                       "<p><strong>Pseudo :</strong> " + utilisateur1 + "</p>" +
                       "<p><strong>Nom :</strong> " + utilisateur1 + "</p>" +
                   "</div>" +
               "</div>";

        }
        
    }
    
    
}

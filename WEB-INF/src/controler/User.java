package controler;

import java.time.LocalDate;

public class User {
    private String uPseudo;
    private String email;
    private String prenom;
    private String nom;
    private LocalDate dateInscritption;
    private LocalDate anniversaire;
    private String description;
    private String mdp;

    public User(String UPseudo, String email, String prenom, String nom, LocalDate dateInscritption, LocalDate anniversaire, String description, String mdp) {
        this.uPseudo = UPseudo;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
        this.dateInscritption = LocalDate.now();
        this.anniversaire = anniversaire;
        this.description = description;
        this.mdp = mdp;
    }

    public String getUPseudo() {
        return uPseudo;
    }

    public void setUPseudo(String UPseudo) {
        this.uPseudo = UPseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateInscritption() {
        return dateInscritption;
    }

    public void setDateInscritption(LocalDate dateInscritption) {
        this.dateInscritption = dateInscritption;
    }

    public LocalDate getAnniversaire() {
        return anniversaire;
    }

    public void setAnniversaire(LocalDate anniversaire) {
        this.anniversaire = anniversaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}

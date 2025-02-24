package modele;

import java.time.LocalDate;

public class User {
    private String UPseudo;
    private String Email;
    private String Prenom;
    private String Nom;
    private LocalDate DateInscritption;
    private LocalDate Anniversaire;
    private String Description;
    private String mpd;

    public User(String UPseudo, String email, String prenom, String nom, LocalDate dateInscritption, LocalDate anniversaire, String description, String mpd) {
        this.UPseudo = UPseudo;
        Email = email;
        Prenom = prenom;
        Nom = nom;
        DateInscritption = LocalDate.now();
        Anniversaire = anniversaire;
        Description = description;
        this.mpd = mpd;
    }

    public String getUPseudo() {
        return UPseudo;
    }

    public void setUPseudo(String UPseudo) {
        this.UPseudo = UPseudo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public LocalDate getDateInscritption() {
        return DateInscritption;
    }

    public void setDateInscritption(LocalDate dateInscritption) {
        DateInscritption = dateInscritption;
    }

    public LocalDate getAnniversaire() {
        return Anniversaire;
    }

    public void setAnniversaire(LocalDate anniversaire) {
        Anniversaire = anniversaire;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMpd() {
        return mpd;
    }

    public void setMpd(String mpd) {
        this.mpd = mpd;
    }
}

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
    private byte[] photo;

    public User(String UPseudo, String email, String prenom, String nom, LocalDate dateInscritption, LocalDate anniversaire, String description, String mdp, byte[] photo) {
        this.uPseudo = UPseudo;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
        this.dateInscritption = LocalDate.now();
        this.anniversaire = anniversaire;
        this.description = description;
        this.mdp = mdp;
        this.photo = photo;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uPseudo == null) ? 0 : uPseudo.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((dateInscritption == null) ? 0 : dateInscritption.hashCode());
        result = prime * result + ((anniversaire == null) ? 0 : anniversaire.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((mdp == null) ? 0 : mdp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (uPseudo == null) {
            if (other.uPseudo != null)
                return false;
        } else if (!uPseudo.equals(other.uPseudo))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (dateInscritption == null) {
            if (other.dateInscritption != null)
                return false;
        } else if (!dateInscritption.equals(other.dateInscritption))
            return false;
        if (anniversaire == null) {
            if (other.anniversaire != null)
                return false;
        } else if (!anniversaire.equals(other.anniversaire))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (mdp == null) {
            if (other.mdp != null)
                return false;
        } else if (!mdp.equals(other.mdp))
            return false;
        return true;
    }
     
}

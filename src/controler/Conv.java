package modele;

import java.time.LocalDate;

public class Conv {
    private String CID;
    private String Titre;
    private boolean Privee;
    private String Owner;
    private LocalDate Date;

    public Conv(String CID, String titre, boolean privee, String owner, LocalDate date) {
        this.CID = CID;
        Titre = titre;
        Privee = privee;
        Owner = owner;
        Date = date;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public boolean isPrivee() {
        return Privee;
    }

    public void setPrivee(boolean privee) {
        Privee = privee;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }
}

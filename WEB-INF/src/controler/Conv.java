package controler;

import java.time.LocalDate;

public class Conv {
    private String cno;
    private String titre;
    private boolean privee;
    private String owner;
    private LocalDate date;

    public Conv(String cno, String titre, boolean privee, String owner, LocalDate date) {
        this.cno = cno;
        this.titre = titre;
        this.privee = privee;
        this.owner = owner;
        this.date = date;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String CID) {
        this.cno = CID;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isPrivee() {
        return privee;
    }

    public void setPrivee(boolean privee) {
        this.privee = privee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

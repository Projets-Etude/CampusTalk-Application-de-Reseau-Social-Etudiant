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

    @Override
    public String toString() {
        String privacyStatusClass = this.privee ? "private" : "public";
        String privacyStatusText = this.privee ? "Privée" : "Publique";
        
        // Lien vers la page JSP, ici tu peux ajuster l'URL selon ton besoin
        String url = "/sae-s4.a02.1/conversation.jsp?id=" + cno;  // On suppose que chaque conversation a un id unique
        
        return String.format(
            "<div class=\"conversation-card\">\n" +
            "    <div class=\"conversation-header\">\n" +
            "        <a href=\"%s\" class=\"conversation-title\">%s</a>\n" +
            "        <span class=\"conversation-owner\">Par %s</span>\n" +
            "    </div>\n" +
            "    <div class=\"conversation-details\">\n" +
            "        <span class=\"privacy-status %s\">%s</span>\n" +
            "        <span class=\"conversation-date\">%s</span>\n" +
            "    </div>\n" +
            "</div>",
            url, titre, owner, privacyStatusClass, privacyStatusText, date
        );
    }
    
    
    
}

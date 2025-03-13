package controler;

import modele.ConvDAO;

public class ConvDuo {
    private String cid;      // ID de la conversation
    private String upseudo1; // Premier utilisateur
    private String upseudo2; // Deuxième utilisateur

    // Constructeur
    public ConvDuo(String cid, String upseudo1, String upseudo2) {
        this.cid = cid;
        this.upseudo1 = upseudo1;
        this.upseudo2 = upseudo2;
    }

    // Getters et Setters
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUPseudo1() {
        return upseudo1;
    }

    public void setUPseudo1(String upseudo1) {
        this.upseudo1 = upseudo1;
    }

    public String getUPseudo2() {
        return upseudo2;
    }

    public void setUPseudo2(String upseudo2) {
        this.upseudo2 = upseudo2;
    }

    @Override
    public String toString() {
        String privacyStatusClass = "private";
        String privacyStatusText = "Privée";
        
        // Lien vers la page JSP, ici tu peux ajuster l'URL selon ton besoin
        String url = "/sae-s4.a02.1/conversation.jsp?id=" + cid;  // On suppose que chaque conversation a un id unique
        ConvDAO convDAO = new ConvDAO();
        Conv conv = convDAO.findByID(cid);
        return String.format(
            "<div class=\"conversation-card\">\n" +
            "    <div class=\"conversation-header\">\n" +
            "        <a href=\"%s\" class=\"conversation-title\">%s</a>\n" +          
            "    </div>\n" +
            "    <div class=\"conversation-details\">\n" +
            "        <span class=\"privacy-status %s\">%s</span>\n" +
            "        <span class=\"conversation-date\">%s</span>\n" +
            "    </div>\n" +
            "</div>",
            url, conv.getTitre(), privacyStatusClass, privacyStatusText, conv.getDate()
        );
    }
    

}
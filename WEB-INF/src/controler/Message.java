package controler;

import java.time.LocalDate;

public class Message {
    private String mno;
    private String contenue;
    private LocalDate date;
    private String auteur;
    private byte[] image;

    public Message(String mno, String contenue, LocalDate date, String auteur, byte[] image) {
        this.mno = mno;
        this.contenue = contenue;
        this.date = date;
        this.auteur = auteur;
        this.image = image;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageBase64() {
        return ImageUtils.getMessageImagePath(this);
    }

    @Override
    public String toString() {

    String messageClass = this.auteur.equals("admin") ? "admin" : "user";
    String imageHtml = getImageBase64().isEmpty() ? "" : "<img src='" + getImageBase64() + "' alt='Image du message'/>";

        return "<div class=\"message " + messageClass + "\">\n" +
                "    <div class=\"author\">" + auteur + "</div>\n" +
                "    <div class=\"content\">" + contenue + "</div>\n" +
                "    <div class=\"time\">" + date + "</div>\n" +
                "    <div class=\"image\">" + imageHtml + "</div>\n" +
                "</div>";
    }
}

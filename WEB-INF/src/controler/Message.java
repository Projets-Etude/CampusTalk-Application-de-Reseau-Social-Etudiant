package controler;

import java.time.LocalDate;

public class Message {
    private String mno;
    private String contenue;
    private LocalDate date;
    private String auteur;

    public Message(String mno, String contenue, LocalDate date, String auteur) {
        this.mno = mno;
        this.contenue = contenue;
        this.date = date;
        this.auteur = auteur;
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

    @Override
    public String toString() {

    String messageClass = this.auteur.equals("admin") ? "admin" : "user";
    
    return "<div class=\"message " + messageClass + "\">\n" +
           "    <div class=\"author\">" + auteur + "</div>\n" +
           "    <div class=\"content\">" + contenue + "</div>\n" +
           "    <div class=\"time\">" + date + "</div>\n" +
           "</div>";
        }

}

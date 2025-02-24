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
        contenue = contenue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        date = date;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        auteur = auteur;
    }
}

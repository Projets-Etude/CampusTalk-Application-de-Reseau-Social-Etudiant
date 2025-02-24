package modele;

import java.time.LocalDate;

public class Message {
    private String MID;
    private String Contenue;
    private LocalDate Date;
    private String Auteur;

    public Message(String MID, String contenue, LocalDate date, String auteur) {
        this.MID = MID;
        Contenue = contenue;
        Date = date;
        Auteur = auteur;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getContenue() {
        return Contenue;
    }

    public void setContenue(String contenue) {
        Contenue = contenue;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public String getAuteur() {
        return Auteur;
    }

    public void setAuteur(String auteur) {
        Auteur = auteur;
    }
}

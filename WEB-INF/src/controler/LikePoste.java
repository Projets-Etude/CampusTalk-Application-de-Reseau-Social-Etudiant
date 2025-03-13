package controler;


public class LikePoste {
    private String posteID;
    private String uPseudo;

    public LikePoste(String posteID, String uPseudo) {
        this.posteID = posteID;
        this.uPseudo = uPseudo;
    }

    public String getPosteID() {
        return posteID;
    }

    public void setPosteID(String posteID) {
        this.posteID = posteID;
    }

    public String getUPseudo() {
        return uPseudo;
    }

    public void setUPseudo(String uPseudo) {
        this.uPseudo = uPseudo;
    }

    @Override
    public String toString() {
        return "LikePoste{" +
                "posteID='" + posteID + '\'' +
                ", uPseudo='" + uPseudo + '\'' +
                '}';
    }
}


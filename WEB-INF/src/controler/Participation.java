package controler;

public class Participation {

    private User utilisateur;
    private Conv conversation;

    
    public Participation(User utilisateur, Conv conversation) {
        this.utilisateur = utilisateur;
        this.conversation = conversation;
    }


    public User getUtilisateur() {
        return utilisateur;
    }


    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }


    public Conv getConversation() {
        return conversation;
    }


    public void setConversation(Conv conversation) {
        this.conversation = conversation;
    }

    
    
}

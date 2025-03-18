# CampusTalk - Application de Réseau Social Étudiant

## Table des matières
- [Introduction](#introduction)
- [Architecture du Projet](#architecture)
- [Structure de la Base de Données](#structure-bd)
- [Fonctionnalités](#fonctionnalités)
- [Sécurité](#sécurité)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Points Techniques Difficiles](#points-techniques-difficiles)

## Introduction
CampusTalk est une application web de réseau social développée en Java EE, permettant aux étudiants de créer des conversations privées ou publiques, de partager des messages et des images, et de gérer leurs relations avec d'autres utilisateurs. L'application suit une architecture MVC et utilise PostgreSQL comme système de gestion de base de données.

## Architecture du Projet
L'application suit une architecture MVC avec une séparation claire des responsabilités :

### Structure des Dossiers
```markdown
.
├── META-INF
├── README.md
├── WEB-INF
│   ├── classes
│   │   ├── controler
│   │   └── modele
│   └── src
│       ├── controler
│       │   ├── Abonnement.java
│       │   ├── AjouterAbo.java
│       │   ├── Conv.java
│       │   ├── ConvDuo.java
│       │   ├── ConversationsRestAPI.java
│       │   ├── CreateConv.java
│       │   ├── CreateMessage.java
│       │   ├── CreatePoste.java
│       │   ├── ImageUtils.java
│       │   ├── LikePoste.java
│       │   ├── LikeUnlikePoste.java
│       │   ├── Logout.java
│       │   ├── Message.java
│       │   ├── ModifConv.java
│       │   ├── Participation.java
│       │   ├── Poste.java
│       │   ├── SupprimeAbo.java
│       │   └── User.java
│       └── modele
│           ├── AbonnementDAO.java
│           ├── ConvDAO.java
│           ├── DS.java
│           ├── LikePosteDAO.java
│           ├── Login.java
│           ├── MessageDAO.java
│           ├── Modification.java
│           ├── ParticipationDAO.java
│           ├── PosteDAO.java
│           ├── Register.java
│           ├── UserDAO.java
│           └── config.postgres.prop
├── accueil.css
├── accueil.jsp
├── conversation.css
├── conversation.jsp
├── login.html
├── res
│   ├── ModèleMCD.png
│   ├── avatar.png
│   ├── message.png
│   ├── script.sql
│   └── todo
└── style.css
```

## Structure de la Base de Données
Le système d'information est structuré autour de plusieurs entités principales :

### MCD (Modèle Conceptuel de Données)
```markdown
- UTILISATEUR
  * UPseudo (identifiant unique)
  * Email
  * Prenom
  * Nom
  * DateInscription
  * Anniversaire
  * Description
  * Mdp
  * Photo

- CONVERSATION
  * CID (identifiant unique)
  * Titre
  * Privee (booléen)
  * Duo (booléen)
  * Owner (référence à UTILISATEUR)

- MESSAGE
  * MID (identifiant unique)
  * Contenue
  * DateEnvoie
  * Auteur (référence à UTILISATEUR)
  * CID (référence à CONVERSATION)
  * Image

- POSTE
  * PosteID (identifiant unique)
  * Contenu
  * DatePoste
  * VisibiliteRestreinte
  * UPseudo (référence à UTILISATEUR)

- LIKEPOSTE
  * PosteID (référence à POSTE)
  * UPseudo (référence à UTILISATEUR)
  * DateLike

- ABONNEMENT
  * AbonnementID (identifiant unique)
  * Utilisateur1 (référence à UTILISATEUR)
  * Utilisateur2 (référence à UTILISATEUR)
  * DateAbonnement

- PARTICIPATION
  * UPseudo (référence à UTILISATEUR)
  * CID (référence à CONVERSATION)
  * DateEntree

- CONVERSATIONDUO
  * CID (référence à CONVERSATION)
  * User1 (référence à UTILISATEUR)
  * User2 (référence à UTILISATEUR)
```

## Fonctionnalités
L'application implémente les fonctionnalités suivantes :

### 1. Gestion des Utilisateurs
- Inscription avec validation d'email
- Connexion sécurisée
- Profil personnel avec photo
- Gestion des abonnements

### 2. Conversations
- Création de conversations privées ou publiques
- Support des conversations en duo
- Gestion des participants
- Messages avec support d'images

### 3. Publications
- Création et gestion de posts
- Système de likes
- Partage d'images
- Visibilité restreinte optionnelle

## Contrôleurs et Fonctionnalités
Les contrôleurs principaux sont :

### 1. Gestion des Utilisateurs
```java
package controler;

import java.time.LocalDate;

public class User {
    private String uPseudo;
    private String email;
    private String prenom;
    private String nom;
    private LocalDate dateInscritption;
    private LocalDate anniversaire;
    private String description;
    private String mdp;
    private byte[] photo;

    public User(String UPseudo, String email, String prenom, String nom, LocalDate dateInscritption, LocalDate anniversaire, String description, String mdp, byte[] photo) {
        this.uPseudo = UPseudo;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
        this.dateInscritption = LocalDate.now();
        this.anniversaire = anniversaire;
        this.description = description;
        this.mdp = mdp;
        this.photo = photo;
    }

    public String getUPseudo() {
        return uPseudo;
    }

    public void setUPseudo(String UPseudo) {
        this.uPseudo = UPseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateInscritption() {
        return dateInscritption;
    }

    public void setDateInscritption(LocalDate dateInscritption) {
        this.dateInscritption = dateInscritption;
    }

    public LocalDate getAnniversaire() {
        return anniversaire;
    }

    public void setAnniversaire(LocalDate anniversaire) {
        this.anniversaire = anniversaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uPseudo == null) ? 0 : uPseudo.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((dateInscritption == null) ? 0 : dateInscritption.hashCode());
        result = prime * result + ((anniversaire == null) ? 0 : anniversaire.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((mdp == null) ? 0 : mdp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (uPseudo == null) {
            if (other.uPseudo != null)
                return false;
        } else if (!uPseudo.equals(other.uPseudo))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (dateInscritption == null) {
            if (other.dateInscritption != null)
                return false;
        } else if (!dateInscritption.equals(other.dateInscritption))
            return false;
        if (anniversaire == null) {
            if (other.anniversaire != null)
                return false;
        } else if (!anniversaire.equals(other.anniversaire))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (mdp == null) {
            if (other.mdp != null)
                return false;
        } else if (!mdp.equals(other.mdp))
            return false;
        return true;
    }
     
}

```

### 2. Gestion des Conversations
```java
package controler;

import java.time.LocalDate;

public class Conv {
    private String cno;
    private String titre;
    private boolean privee;

    private boolean duo;
    private String owner;
    private LocalDate date;

    public Conv(String cno, String titre, boolean privee, boolean duo, String owner, LocalDate date) {
        this.cno = cno;
        this.titre = titre;
        this.privee = privee;
        this.duo = duo;
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

    public boolean isDuo(){
        return duo;
    }

    public void setDuo(boolean duo){
        this.duo = duo;
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
        
        String url = "/sae-s4.a02.1/conversation.jsp?id=" + cno;
        
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

```

### 3. Gestion des Messages
```java
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

```

### 4. Gestion des Posts
```java
package controler;

import java.util.Base64;
import java.util.Date;

import modele.LikePosteDAO;

import modele.UserDAO;

public class Poste {
    private String posteID;
    private String contenu;
    private Date datePoste;
    private boolean visibiliteRestreinte;
    private String uPseudo;

    public Poste(String posteID, String contenu, Date datePoste, boolean visibiliteRestreinte, String uPseudo) {
        this.posteID = posteID;
        this.contenu = contenu;
        this.datePoste = datePoste;
        this.visibiliteRestreinte = visibiliteRestreinte;
        this.uPseudo = uPseudo;
    }

    public String getPosteID() {
        return posteID;
    }

    public String getContenu() {
        return contenu;
    }

    public Date getDatePoste() {
        return datePoste;
    }

    public boolean isVisibiliteRestreinte() {
        return visibiliteRestreinte;
    }

    public String getUPseudo() {
        return uPseudo;
    }

    public void setPosteID(String posteID) {
        this.posteID = posteID;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDatePoste(Date datePoste) {
        this.datePoste = datePoste;
    }

    public void setVisibiliteRestreinte(boolean visibiliteRestreinte) {
        this.visibiliteRestreinte = visibiliteRestreinte;
    }

    public void setUPseudo(String uPseudo) {
        this.uPseudo = uPseudo;
    }

    public String getPosteHTML(String userPseudo, LikePosteDAO likePosteDAO, boolean isFollowing, boolean isCurrentUser, boolean canFollow) {
        boolean hasLike = likePosteDAO.hasLiked(this.getPosteID(), userPseudo);
        String photoPath = ImageUtils.getProfileImagePath(new UserDAO().findByID(this.uPseudo));

        StringBuilder html = new StringBuilder();
        html.append("<div class='tweet'>")
                .append("<div class='tweet-header'>")
                .append("<img class='tweet-profil' src='").append(photoPath).append("' alt='").append(this.getUPseudo()).append(" Avatar'>")
                .append("<div class='tweet-info'>")
                .append("<strong>").append(this.getUPseudo()).append("</strong>")
                .append("<div class='tweet-date'>").append(this.getDatePoste()).append("</div>")
                .append("</div>")
                .append("</div>")
                .append("<div class='tweet-content'>").append(this.getContenu()).append("</div>");

        if (this.isVisibiliteRestreinte()) {
            html.append("<div class='tweet-private'>🔒 Privé</div>");
        }

        html.append("<div class='tweet-footer'>");

        if (isFollowing) {
            html.append("<button class='followed-button'>Suivi</button>");
        } else if (canFollow) {
            html.append("<a href='suivreUtilisateur?utilisateur=").append(this.getUPseudo()).append("' class='follow-button'>Suivre</a>");
        }

        html.append("<form action='LikePosteServlet' method='post' class='like-form'>")
                .append("<input type='hidden' name='posteID' value='").append(this.getPosteID()).append("'>")
                .append("<button type='submit' class='like-button ").append(hasLike ? "liked" : "").append("'>")
                .append(hasLike ? "❤️" : "🤍").append(" " + likePosteDAO.countLikes(this.getPosteID()))
                .append("</button>")
                .append("</form>")
                .append("</div>")
                .append("</div>");

        return html.toString();
    }
}


```

### 5. Gestion des Likes
```java
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

}


```

## Points Techniques Difficiles
1. Gestion des Conversations :
   - Synchronisation des messages en temps réel
   - Gestion efficace des participants
   - Support des conversations en duo

2. Sécurité :
   - Protection contre les injections SQL
   - Validation des fichiers uploadés
   - Gestion sécurisée des sessions

3. Performance :
   - Optimisation des requêtes SQL
   - Cache des données fréquentes
   - Gestion efficace des ressources

4. Architecture :
   - Séparation claire des responsabilités
   - Gestion des dépendances
   - Maintenance du code

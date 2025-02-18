CREATE DATABASE IF NOT EXISTS WhatsUp;

CREATE TABLE IF NOT EXISTS Utilisateur (
    UPseudo VARCHAR(50),
    Email VARCHAR(100) UNIQUE NOT NULL,
    Prenom VARCHAR(50),
    Nom VARCHAR(50),
    DateInscription DATE,
    Anniversaire DATE,
    Description TEXT,
    Mdp VARCHAR(255) NOT NULL,
    CONSTRAINT PK_Utilisateur PRIMARY KEY (UPseudo)
);

CREATE TABLE IF NOT EXISTS Conversation (
    CID VARCHAR(50),
    Titre VARCHAR(100) NOT NULL,
    Privee BOOLEAN,
    Owner VARCHAR(50),
    DateCreation DATE,
    CONSTRAINT PK_Conversation PRIMARY KEY (CID),
    CONSTRAINT FK_Conversation_Owner FOREIGN KEY (Owner) REFERENCES Utilisateur(UPseudo) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Message (
    MID VARCHAR(50),
    Contenue TEXT NOT NULL,
    DateEnvoie DATE,
    Auteur VARCHAR(50),
    CID VARCHAR(50),
    CONSTRAINT PK_Message PRIMARY KEY (MID),
    CONSTRAINT FK_Message_Auteur FOREIGN KEY (Auteur) REFERENCES Utilisateur(UPseudo) ON DELETE CASCADE,
    CONSTRAINT FK_Message_Conversation FOREIGN KEY (CID) REFERENCES Conversation(CID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Poste (
    PosteID VARCHAR(50),
    Contenu TEXT NOT NULL,
    DatePoste DATE,
    VisibiliteRestreinte BOOLEAN,
    UPseudo VARCHAR(50),
    CONSTRAINT PK_Poste PRIMARY KEY (PosteID),
    CONSTRAINT FK_Poste_Utilisateur FOREIGN KEY (UPseudo) REFERENCES Utilisateur(UPseudo) ON DELETE CASCADE
);
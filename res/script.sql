--CREATE DATABASE IF NOT EXISTS WhatsUp;

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
    CONSTRAINT FK_Conversation_Owner FOREIGN KEY (Owner) REFERENCES Utilisateur(UPseudo) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Message (
    MID VARCHAR(50),
    Contenue TEXT NOT NULL,
    DateEnvoie DATE,
    Auteur VARCHAR(50),
    CID VARCHAR(50),
    CONSTRAINT PK_Message PRIMARY KEY (MID),
    CONSTRAINT FK_Message_Auteur FOREIGN KEY (Auteur) REFERENCES Utilisateur(UPseudo) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Message_Conversation FOREIGN KEY (CID) REFERENCES Conversation(CID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Poste (
    PosteID VARCHAR(50),
    Contenu TEXT NOT NULL,
    DatePoste DATE,
    VisibiliteRestreinte BOOLEAN,
    UPseudo VARCHAR(50),
    CONSTRAINT PK_Poste PRIMARY KEY (PosteID),
    CONSTRAINT FK_Poste_Utilisateur FOREIGN KEY (UPseudo) REFERENCES Utilisateur(UPseudo) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS LikePoste (
    PosteID VARCHAR(50),
    UPseudo VARCHAR(50),
    DateLike TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_LikePoste PRIMARY KEY (PosteID, UPseudo),
    CONSTRAINT FK_LikePoste_Poste FOREIGN KEY (PosteID) REFERENCES Poste(PosteID) ON DELETE CASCADE,
    CONSTRAINT FK_LikePoste_Utilisateur FOREIGN KEY (UPseudo) REFERENCES Utilisateur(UPseudo) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Participation (
    UPseudo VARCHAR(50),
    CID VARCHAR(50),
    DateEntree DATE DEFAULT CURRENT_DATE,
    CONSTRAINT PK_Participation PRIMARY KEY (UPseudo, CID),
    CONSTRAINT FK_Participation_Utilisateur FOREIGN KEY (UPseudo) REFERENCES Utilisateur(UPseudo) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Participation_Conversation FOREIGN KEY (CID) REFERENCES Conversation(CID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Abonnement (
    AbonnementID VARCHAR(50) PRIMARY KEY,
    Utilisateur1 VARCHAR(50),
    Utilisateur2 VARCHAR(50),
    DateAbonnement DATE DEFAULT CURRENT_DATE,
    CONSTRAINT FK_Abonnement_Utilisateur1 FOREIGN KEY (Utilisateur1) REFERENCES Utilisateur(UPseudo) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Abonnement_Utilisateur2 FOREIGN KEY (Utilisateur2) REFERENCES Utilisateur(UPseudo) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT UNQ_Abonnement UNIQUE (Utilisateur1, Utilisateur2)
);



INSERT INTO Poste (PosteID, Contenu, DatePoste, VisibiliteRestreinte, UPseudo) VALUES
('P001', 'Bonjour tout le monde !', '2024-02-20', FALSE, 'Dutax'),
('P002', 'Quelqu''un a des recommandations de livres ?', '2024-02-21', TRUE, 'Dutax'),
('P003', 'Le soleil est enfin là !', '2024-02-22', FALSE, 'Dutax'),
('P004', 'Petit café du matin ☕', '2024-02-23', FALSE, 'Dutax'),
('P005', 'Grosse mise à jour sur mon projet de dev !', '2024-02-24', TRUE, 'Dutax'),
('P006', 'Les chats sont supérieurs aux chiens. Convainquez-moi du contraire.', '2024-02-25', FALSE, 'Dutax'),
('P007', 'Nouvelle photo de mon voyage au Japon', '2024-02-26', TRUE, 'Dutax'),
('P008', 'Un bug mystérieux me hante... 😱', '2024-02-27', FALSE, 'Dutax'),
('P009', 'La musique Lo-Fi, c''est la meilleure pour bosser.', '2024-02-28', FALSE, 'Dutax'),
('P010', 'J’ai découvert un resto incroyable à Lille !', '2024-02-29', TRUE, 'Dutax'),
('P011', 'Nouvelle vidéo sur ma chaîne YouTube !', '2024-03-01', FALSE, 'Dutax'),
('P012', 'Les IA prennent le contrôle 😆', '2024-03-02', FALSE, 'Dutax'),
('P013', 'Apprendre une nouvelle langue, challenge accepté !', '2024-03-03', TRUE, 'Dutax'),
('P014', 'Pourquoi le Java est-il si controversé ?', '2024-03-04', FALSE, 'Dutax'),
('P015', 'Bon dimanche à tous !', '2024-03-05', FALSE, 'Dutax');


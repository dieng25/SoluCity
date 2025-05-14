CREATE TABLE Citoyen(
                        tel_num VARCHAR(50),
                        Nom VARCHAR(50) NOT NULL,
                        Prenom VARCHAR(50) NOT NULL,
                        email VARCHAR(50) NOT NULL,
                        Identifiant VARCHAR(50) NOT NULL,
                        PRIMARY KEY(tel_num)
);

CREATE TABLE Mairie(
                       Code_Postal VARCHAR(50),
                       PRIMARY KEY(Code_Postal)
);

CREATE TABLE Fonctionnaire(
                              email VARCHAR(50),
                              mdp VARCHAR(50) NOT NULL,
                              Code_Postal VARCHAR(50) NOT NULL,
                              PRIMARY KEY(email),
                              FOREIGN KEY(Code_Postal) REFERENCES Mairie(Code_Postal)
);

CREATE TABLE CategorieIncident(
                                  CategorieIncident VARCHAR(50),
                                  PRIMARY KEY(CategorieIncident)
);

CREATE TABLE CategorieSuggestion(
                                    CategorieSuggestion VARCHAR(50),
                                    PRIMARY KEY(CategorieSuggestion)
);

CREATE TABLE Incident(
                         Id_ticket INT NOT NULL AUTO_INCREMENT,
                         Titre VARCHAR(50) NOT NULL,
                         Description TEXT NOT NULL,
                         date_creation DATE NOT NULL,
                         Categorie VARCHAR(50) NOT NULL,
                         Statut INT,
                         CodePostal_ticket VARCHAR(50) NOT NULL,
                         Priorite INT,
                         date_cloture DATE,
                         tel_num VARCHAR(50) NOT NULL,
                         Code_Postal VARCHAR(50) NOT NULL,
                         CategorieIncident VARCHAR(50) NOT NULL,
                         PRIMARY KEY(Id_ticket),
                         FOREIGN KEY(tel_num) REFERENCES Citoyen(tel_num),
                         FOREIGN KEY(Code_Postal) REFERENCES Mairie(Code_Postal),
                         FOREIGN KEY(CategorieIncident) REFERENCES CategorieIncident(CategorieIncident)
);

CREATE TABLE Suggestion(
                           Id_ticket INT NOT NULL AUTO_INCREMENT,
                           Titre VARCHAR(50) NOT NULL,
                           Description TEXT NOT NULL,
                           date_creation DATE NOT NULL,
                           Categorie VARCHAR(50) NOT NULL,
                           Statut INT,
                           CodePostal_ticket VARCHAR(50) NOT NULL,
                           date_cloture DATE,
                           Commentaire TEXT,
                           Code_Postal VARCHAR(50) NOT NULL,
                           tel_num VARCHAR(50) NOT NULL,
                           CategorieSuggestion VARCHAR(50) NOT NULL,
                           PRIMARY KEY(Id_ticket),
                           FOREIGN KEY(Code_Postal) REFERENCES Mairie(Code_Postal),
                           FOREIGN KEY(tel_num) REFERENCES Citoyen(tel_num),
                           FOREIGN KEY(CategorieSuggestion) REFERENCES CategorieSuggestion(CategorieSuggestion)
);

CREATE TABLE Administrateur (
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(50) NOT NULL
);

CREATE TABLE Acces (
    CodePostal VARCHAR(50) PRIMARY KEY,
    Utilisateur VARCHAR(50) NOT NULL,
    MotDePasse VARCHAR(50) NOT NULL
);
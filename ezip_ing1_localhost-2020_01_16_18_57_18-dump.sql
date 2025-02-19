--

--
CREATE TABLE Citoyen(
                        tel_num VARCHAR(50),
                        Nom VARCHAR(50) NOT NULL,
                        Prénom VARCHAR(50) NOT NULL,
                        email VARCHAR(50),
                        Identifiant VARCHAR(50) NOT NULL,
                        PRIMARY KEY(tel_num),
                        UNIQUE(email),
                        UNIQUE(Identifiant)
);

CREATE TABLE Mairie(
                       Code_Postal VARCHAR(50),
                       email VARCHAR(50),
                       mdp VARCHAR(50) NOT NULL,
                       PRIMARY KEY(Code_Postal),
                       UNIQUE(email)
);

CREATE TABLE Suggestion(
                           Id_ticket VARCHAR(50),
                           Titre VARCHAR(50),
                           Description VARCHAR(50),
                           date_emis DATE,
                           Catégorie VARCHAR(50),
                           statut INT,
                           CodePostal_ticket INT,
                           tel_num VARCHAR(50) NOT NULL,
                           PRIMARY KEY(Id_ticket),
                           FOREIGN KEY(tel_num) REFERENCES Citoyen(tel_num)
);

CREATE TABLE Incident(
                         Id_ticket VARCHAR(50),
                         Titre VARCHAR(50) NOT NULL,
                         Description VARCHAR(50) NOT NULL,
                         date_emis DATE NOT NULL,
                         Catégorie VARCHAR(50) NOT NULL,
                         Statut INT NOT NULL,
                         CodePostal_ticket VARCHAR(50) NOT NULL,
                         Priorité INT,
                         tel_num VARCHAR(50) NOT NULL,
                         Code_Postal VARCHAR(50) NOT NULL,
                         PRIMARY KEY(Id_ticket),
                         FOREIGN KEY(tel_num) REFERENCES Citoyen(tel_num),
                         FOREIGN KEY(Code_Postal) REFERENCES Mairie(Code_Postal)
);


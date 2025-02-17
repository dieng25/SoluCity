--

--
CREATE TABLE Citoyen(
tel_num VARCHAR(50),
Nom VARCHAR(50) NOT NULL,
Prénom VARCHAR(50) NOT NULL,
email VARCHAR(50),
PRIMARY KEY(tel_num),
UNIQUE(email)
);

CREATE TABLE Mairie(
Code_Postal VARCHAR(50),
email VARCHAR(50),
mdp VARCHAR(50) NOT NULL,
PRIMARY KEY(Code_Postal),
UNIQUE(email)
);

CREATE TABLE Incident_Suggestion(
Id_ticket VARCHAR(50),
Type VARCHAR(50) NOT NULL,
Titre VARCHAR(50) NOT NULL,
Description VARCHAR(50) NOT NULL,
date_emis DATE NOT NULL,
Catégorie VARCHAR(50) NOT NULL,
Statut VARCHAR(50) NOT NULL,
CP_Incident VARCHAR(50) NOT NULL,
Priorité VARCHAR(50),
tel_num VARCHAR(50) NOT NULL,
Code_Postal VARCHAR(50) NOT NULL,
PRIMARY KEY(Id_ticket),
FOREIGN KEY(tel_num) REFERENCES Citoyen(tel_num),
FOREIGN KEY(Code_Postal) REFERENCES Mairie(Code_Postal)
);


-- DONNÉES DE TEST
-- Insertion des mairies
INSERT INTO Mairie (Code_Postal) VALUES ('95000'), ('75002'), ('75003'), ('94000');

-- Insertion des citoyens
INSERT INTO Citoyen (tel_num, Nom, Prenom, email, Identifiant) VALUES
('0601020304', 'Nicolas', 'Jean', 'jean.dupont@email.com', 'JDUPA123'),
('0605060708', 'Martin', 'Claire', 'claire.martin@email.com', 'CMART456'),
('0611121314', 'Durand', 'Paul', 'paul.durand@email.com', 'PDURA789'),
('0622232425', 'Bernard', 'Sophie', 'sophie.bernard@email.com', 'SBERN321');

-- Insertion des fonctionnaires
INSERT INTO Fonctionnaire (email, mdp, Code_Postal) VALUES
('mairie94000@email.com', 'securepass123', '94000'),
('mairie75002@email.com', 'pass456', '75002'),
('mairie95000@email.com', 'securepass123', '95000'),
('mairie75003@email.com', 'pass456', '75003');

-- Insertion des incidents
INSERT INTO Incident (Id_ticket, Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, Priorite, date_cloture, tel_num, Code_Postal) VALUES
(1, 'Panne éclairage', 'Les lampadaires ne fonctionnent plus.', '2025-03-02', 'Éclairage public', 2, '94000', 3, NULL, '0601020304', '94000'),
(2, 'Fuite d eau', 'Une fuite d eau importante rue de Rivoli.', '2025-03-04', 'Autre', 1, '75002', 2, NULL, '0605060708', '75002'),
(3, 'Trou sur la chaussée', 'Gros trou sur la chaussée avenue de l Opéra.', '2025-03-06', 'Voirie', 3, '75003', 2, NULL, '0611121314', '75003'),
(4, 'Déchets non ramassés', 'Poubelles pleines depuis 3 jours.', '2025-03-07', 'Propreté', 1, '95000', 3, NULL, '0622232425', '95000');

-- Insertion des suggestions
INSERT INTO Suggestion (Id_ticket, Titre, Description, date_creation, Categorie, Statut, CodePostal_ticket, date_cloture, Code_Postal, tel_num) VALUES
(1, 'Plus d espaces verts', 'Créer plus de parcs dans la ville.', '2025-03-02', 'Environnement', 1, '95000', NULL, '95000', '0601020304'),
(2, 'Nouveau Collège', 'Nous souhaitons un collège plus près du centre-ville.', '2025-03-05', 'Infrastructure', 1, '75002', NULL, '75002', '0605060708'),
(3, 'Activité sportive', 'Nous souhaitons que le Volley-ball soit ajouté aux sports proposés au gymnase.', '2025-03-02', 'Sport et Culture', 1, '94000', NULL, '94000', '0601020304'),
(4, 'Patrouille de police', 'Faire plus de patrouilles le soir pour plus de sécurité.', '2025-03-05', 'Sécurité', 1, '75003', NULL, '75003', '0605060708');

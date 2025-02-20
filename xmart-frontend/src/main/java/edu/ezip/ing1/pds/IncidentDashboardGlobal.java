package edu.ezip.ing1.pds;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class IncidentDashboardGlobal extends JFrame {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            // Connecter au serveur
            socket = new Socket("localhost", 45065);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Lire toutes les lignes envoyées par le serveur dans un StringBuilder
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String DataRecu = sb.toString();

            // Vérifier si DataRecu est null ou vide
            if (DataRecu == null || DataRecu.trim().isEmpty()) {
                System.err.println("Aucune donnée reçue du serveur.");
                return;
            }
            
            // Séparation des données sur les incidents et les urgences
            // On attend une chaîne au format "valeursIncidents/valeursUrgence"
            String[] parts = DataRecu.split("/");
            if(parts.length < 2) {
                System.err.println("Données reçues dans un format inattendu : " + DataRecu);
                return;
            }

            // Incidents par le statut : format attendu "total;nonOuvert;enCours;resolu"
            String[] ValeurIncident = parts[0].split(";");
            int totalIncident = Integer.parseInt(ValeurIncident[0]);
            int IncidentNonOuvert = Integer.parseInt(ValeurIncident[1]);
            int IncidentEnCours = Integer.parseInt(ValeurIncident[2]);
            int IncidentResolu = Integer.parseInt(ValeurIncident[3]);

            // Pour la priorité : format attendu "nonDefini;faible;moyen;haut"
            String[] ValeurPriorite = parts[1].split(";");
            int NonDefini = Integer.parseInt(ValeurPriorite[0]);
            int Faible = Integer.parseInt(ValeurPriorite[1]);
            int Moyen = Integer.parseInt(ValeurPriorite[2]);
            int Haut = Integer.parseInt(ValeurPriorite[3]);

            // Calcul des pourcentages pour les niveaux d'urgence
            double PourcentageNonDefini = (totalIncident > 0) ? (double) NonDefini / totalIncident * 100 : 0;
            double PourcentageFaible = (totalIncident > 0) ? (double) Faible / totalIncident * 100 : 0;
            double PourcentageMoyen = (totalIncident > 0) ? (double) Moyen / totalIncident * 100 : 0;
            double PourcentageHaut = (totalIncident > 0) ? (double) Haut / totalIncident * 100 : 0;

            // Création de l'interface graphique
            JFrame frame = new JFrame("Dashboard Global des Incidents");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

            // Création des JTextArea pour afficher les données
            JTextArea TextArea1 = new JTextArea("Total Incidents : " + totalIncident);
            JTextArea TextArea2 = new JTextArea("Dossiers Non Ouverts : " + IncidentNonOuvert);
            JTextArea TextArea3 = new JTextArea("En Cours d'instruction : " + IncidentEnCours);
            JTextArea TextArea4 = new JTextArea("Résolus : " + IncidentResolu);
            JTextArea TextArea5 = new JTextArea(String.format("Urgence Vide : %.2f%%", PourcentageNonDefini));
            JTextArea TextArea6 = new JTextArea(String.format("Urgence Faible : %.2f%%", PourcentageFaible));
            JTextArea TextArea7 = new JTextArea(String.format("Urgence Moyenne : %.2f%%", PourcentageMoyen));
            JTextArea TextArea8 = new JTextArea(String.format("Urgence Élevée : %.2f%%", PourcentageHaut));

            // Ajout des JTextArea à la fenêtre
            frame.add(TextArea1);
            frame.add(TextArea2);
            frame.add(TextArea3);
            frame.add(TextArea4);
            frame.add(TextArea5);
            frame.add(TextArea6);
            frame.add(TextArea7);
            frame.add(TextArea8);

            //frame.pack();
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Fermeture du socket si non null
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

package edu.ezip.ing1.pds;

import java.io.*;
import java.net.*;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.client.commons.ClientRequest;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.DashboardServiceClient;
import edu.ezip.ing1.pds.services.StudentService;

import java.io.IOException;



public class IncidentDashboardGlobal extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public static void main(String[] args) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        final DashboardServiceClient dashboardServiceClient = new DashboardServiceClient(networkConfig);
         DashboardDatas dashboardDatas = dashboardServiceClient.dashboard();
         
         // Initialisation des compteurs globaux
int totalIncident = 0;
int IncidentNonOuvert = 0;
int IncidentEnCours = 0;
int IncidentResolu = 0;
int NonDefini = 0;
int Faible = 0;
int Moyen = 0;
int Haut = 0;

// Parcours de chaque DashboardData dans la collection
for (DashboardData data : dashboardDatas.getDashboardDataSet()) {
    if (dashboardDatas == null || dashboardDatas.getDashboardDataSet() == null) {
        logger.error("Les données du dashboard sont nulles.");
        return;
    }
    totalIncident    += data.getTotalIncident();
    IncidentNonOuvert += data.getIncidentNonOuvert();
    IncidentEnCours  += data.getIncidentEnCours();
    IncidentResolu   += data.getIncidentResolu();
    
    NonDefini += data.getNonDefini();
    Faible    += data.getFaible();
    Moyen     += data.getMoyen();
    Haut      += data.getHaut();
}


            // Calcul des pourcentages pour les niveaux d'urgence
            double PourcentageNonDefini = (totalIncident > 0) ? (double) NonDefini / totalIncident * 100 : 0;
            double PourcentageFaible = (totalIncident > 0) ? (double) Faible / totalIncident * 100 : 0;
            double PourcentageMoyen = (totalIncident > 0) ? (double) Moyen / totalIncident * 100 : 0;
            double PourcentageHaut = (totalIncident > 0) ? (double) Haut / totalIncident * 100 : 0;


            JFrame frame = new JFrame("Dashboard Gobal des Incidents");
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


            frame.add(TextArea1);
            frame.add(TextArea2);
            frame.add(TextArea3);
            frame.add(TextArea4);
            frame.add(TextArea5);
            frame.add(TextArea6);
            frame.add(TextArea7);
            frame.add(TextArea8);

            frame.pack();
            frame.setVisible(true);   
        
    }
}
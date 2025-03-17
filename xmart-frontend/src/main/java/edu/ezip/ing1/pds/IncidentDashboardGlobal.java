package edu.ezip.ing1.pds;

import java.io.*;
import java.net.*;
import java.util.ArrayDeque;
import java.util.Deque;

//import org.jdatepicker.impl.*;
import java.awt.*;
import java.sql.*;
import java.util.Properties;
import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.business.dto.DashboardData;
import edu.ezip.ing1.pds.business.dto.DashboardDatas;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Dashboard.DashboardServiceClient;


import java.io.IOException;



public class IncidentDashboardGlobal extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    public IncidentDashboardGlobal() throws InterruptedException {
        setTitle("Dashboard Global des Incidents");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        try {
            NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            DashboardServiceClient dashboardServiceClient = new DashboardServiceClient(networkConfig);
            DashboardDatas dashboardDatas = dashboardServiceClient.dashboard();

            if (dashboardDatas == null || dashboardDatas.getDashboardDataSet() == null) {
                logger.error("Les données du dashboard sont nulles.");
                return;
            }

            int totalIncident = 0, incidentNonOuvert = 0, incidentEnCours = 0, incidentResolu = 0;
            int nonDefini = 0, faible = 0, moyen = 0, haut = 0;

            for (DashboardData data : dashboardDatas.getDashboardDataSet()) {
                totalIncident += data.getTotalIncident();
                incidentNonOuvert += data.getIncidentNonOuvert();
                incidentEnCours += data.getIncidentEnCours();
                incidentResolu += data.getIncidentResolu();
                nonDefini += data.getNonDefini();
                faible += data.getFaible();
                moyen += data.getMoyen();
                haut += data.getHaut();
            }

            // Calcul des pourcentages
            double pourcentageNonDefini = (totalIncident > 0) ? (double) nonDefini / totalIncident * 100 : 0;
            double pourcentageFaible = (totalIncident > 0) ? (double) faible / totalIncident * 100 : 0;
            double pourcentageMoyen = (totalIncident > 0) ? (double) moyen / totalIncident * 100 : 0;
            double pourcentageHaut = (totalIncident > 0) ? (double) haut / totalIncident * 100 : 0;

            // Pour l'affichage des données
            JTextArea[] textAreas = {
                new JTextArea("Total Incidents : " + totalIncident),
                new JTextArea("Dossiers Non Ouverts : " + incidentNonOuvert),
                new JTextArea("En Cours d'instruction : " + incidentEnCours),
                new JTextArea("Résolus : " + incidentResolu),
                new JTextArea(String.format("Urgence Vide : %.2f%%", pourcentageNonDefini)),
                new JTextArea(String.format("Urgence Faible : %.2f%%", pourcentageFaible)),
                new JTextArea(String.format("Urgence Moyenne : %.2f%%", pourcentageMoyen)),
                new JTextArea(String.format("Urgence Élevée : %.2f%%", pourcentageHaut))
            };

            for (JTextArea textArea : textAreas) {
                textArea.setEditable(false);
                add(textArea);
            }

            //pack();
            setVisible(true);

        } catch (IOException e) {
            logger.error("Erreur lors du chargement des données", e);
        }
    }
}
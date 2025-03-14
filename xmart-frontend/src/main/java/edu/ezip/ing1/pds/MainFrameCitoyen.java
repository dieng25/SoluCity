package edu.ezip.ing1.pds;

import edu.ezip.ing1.pds.services.Citoyen.Incident.CategorieIncident;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrameCitoyen {
    public MainFrameCitoyen() {

        //public static void main(String[] args) {

            JFrame mainFrame = new JFrame("Accueil");
            mainFrame.setSize(300, 200);
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setLayout(new BorderLayout());

            JButton incidentButton = new JButton("Incident");
            incidentButton.addActionListener(e -> {
                        new CategorieIncident();
                        mainFrame.dispose();
            });

            mainFrame.add(incidentButton, BorderLayout.CENTER);

            mainFrame.setVisible(true);
        }
}






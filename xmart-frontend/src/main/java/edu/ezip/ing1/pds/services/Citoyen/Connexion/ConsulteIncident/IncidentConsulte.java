package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteIncident;

import edu.ezip.ing1.pds.business.dto.Incident;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class IncidentConsulte extends JFrame {

    public IncidentConsulte(List<Incident> incidents) {
        setTitle("Consulter les Incidents");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        TableModel model = new IncidentTableModel(incidents);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        Incident incident = incidents.get(row);
                        showIncidentDetails(incident);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showIncidentDetails(Incident incident) {
        JFrame detailsFrame = new JFrame("Détails de l'Incident");
        detailsFrame.setSize(400, 300);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setLayout(new GridLayout(0, 1));

        detailsFrame.add(new JLabel("Priorité: " + incident.getPriorite()));
        detailsFrame.add(new JLabel("Description: " + incident.getDescription()));

        detailsFrame.setVisible(true);
    }


}

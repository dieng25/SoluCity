package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteIncident;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.AccueilConnexion;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class IncidentConsulte extends JFrame {

    public IncidentConsulte(List<Incident> incidents, Citoyen citoyen) {
        setTitle("Consulter les Incidents");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Ticket Incidents", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

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
                        new ShowIncidentDetails(incident);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new AccueilConnexion(citoyen);
            }
        });

        add(mainPanel);
        setVisible(true);
    }




}

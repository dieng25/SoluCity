package edu.ezip.ing1.pds.DashboardFenetre;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.business.dto.DashboardDto.DashboardFilterDTO;
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatIncidentData;
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatIncidentDatas;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Dashboard.StatIncidentService;

public class IncidentIHM extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private JDatePickerImpl datePickerStart;
    private JDatePickerImpl datePickerEnd;
    private JComboBox<String> codePostalComboBox;
    private JPanel chartPanel;
    private String codePostal;

    public IncidentIHM() throws InterruptedException, IOException {
        setTitle("Statistiques sur les Incidents");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        applyFilters();

        setVisible(true);
    }

    private void initializeComponents() {
        UtilDateModel modelStart = new UtilDateModel();
        UtilDateModel modelEnd = new UtilDateModel();
        modelStart.setSelected(true);
        modelEnd.setSelected(true);

        Properties p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");

        JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, p);
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, p);

        datePickerStart = new JDatePickerImpl(datePanelStart, null);
        datePickerEnd = new JDatePickerImpl(datePanelEnd, null);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout());

        datePanel.add(new JLabel("Date de début:"));
        datePanel.add(datePickerStart);

        datePanel.add(new JLabel("Date de fin:"));
        datePanel.add(datePickerEnd);

        JLabel codePostalLabel = new JLabel("Code Postal:");
        codePostalComboBox = new JComboBox<>(new String[] {"tout", "95000", "94000", "75003", "75002"});

        datePanel.add(codePostalLabel);
        datePanel.add(codePostalComboBox);

        JButton applyButton = new JButton("Appliquer");
        applyButton.addActionListener(e -> {
            try {
                applyFilters();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        datePanel.add(applyButton);

        add(datePanel, BorderLayout.NORTH);

        chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(2, 2));
        add(chartPanel, BorderLayout.CENTER);
    }

    private void applyFilters() throws IOException, InterruptedException {
        Date startDate = (Date) datePickerStart.getModel().getValue();
        Date endDate = (Date) datePickerEnd.getModel().getValue();
        codePostal = (String) codePostalComboBox.getSelectedItem();
        System.out.println("Code postal sélectionné : " + codePostal);

        NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        StatIncidentService statIncidentService = new StatIncidentService(networkConfig);
        DashboardFilterDTO filterDTO = new DashboardFilterDTO(startDate, endDate, codePostal);

        StatIncidentDatas statIncidentDatas = statIncidentService.incident(filterDTO);

        if (statIncidentDatas != null) {
            updateCharts(statIncidentDatas);
        } else {
            JOptionPane.showMessageDialog(this, "Les données sont nulles.");
        }
        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner des dates valides.");
            return;
        }

    }
    @SuppressWarnings("unchecked")
    private void updateCharts(StatIncidentDatas statIncidentDatas) {
        chartPanel.removeAll();

    int incidentNonResolu = 0, incidentEnCours = 0, incidentResolu = 0;
    double delaiVoirie = 0, delaiEclairagePublic = 0, delaiEspaceVerts = 0, delaiProprete = 0;
    double delaiAnimauxErrants = 0, delaiAutres = 0;
    String incidentTop1 = null, incidentTop2 = null; List<Incident> incidentUrgent = new ArrayList<>();

    for (StatIncidentData statIncidentData : statIncidentDatas.getStatIncidentDataSet()) {
      
            incidentNonResolu += statIncidentData.getIncidentNonResolu();
            incidentEnCours += statIncidentData.getIncidentEnCours();
            incidentResolu += statIncidentData.getIncidentResolu();

            delaiVoirie += statIncidentData.getDelaiVoirie();
            delaiEclairagePublic += statIncidentData.getDelaiEclairagePublic();
            delaiEspaceVerts += statIncidentData.getDelaiEspaceVerts();
            delaiProprete += statIncidentData.getDelaiProprete();
            delaiAnimauxErrants += statIncidentData.getDelaiAnimauxErrants();
            delaiAutres  += statIncidentData.getDelaiAutres();
        
            incidentTop1 += statIncidentData.getIncidentTop1();
            incidentTop2 += statIncidentData.getIncidentTop2();

            incidentUrgent.addAll(statIncidentData.getIncidentsUrgents());

        }

        // Répartition des incidents par statut
        @SuppressWarnings("rawtypes")
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Non Ouverts", incidentNonResolu);
        pieDataset.setValue("En cours de traitement", incidentEnCours);
        pieDataset.setValue("Résolu", incidentResolu);
        JFreeChart pieChart = ChartFactory.createPieChart("Répartition des Incidents par Statut", pieDataset, true, true, false);
        chartPanel.add(new ChartPanel(pieChart));

        // Délai moyen par catégorie
        @SuppressWarnings("rawtypes")
        DefaultCategoryDataset delayDataset = new DefaultCategoryDataset();
        delayDataset.addValue(delaiVoirie, "Incident", "Voirie");
        delayDataset.addValue(delaiEclairagePublic, "Incident", "Eclairage Public");
        delayDataset.addValue(delaiEspaceVerts, "Incident", "Espaces Verts");
        delayDataset.addValue(delaiProprete, "Incident", "Proprete");
        delayDataset.addValue(delaiAnimauxErrants, "Incident", "Animaux errants ou retrouvés morts");
        delayDataset.addValue(delaiAutres, "Incident", "Autres");

        JFreeChart barChart = ChartFactory.createBarChart("Délai Moyen par Catégorie", "Catégorie", "Jours", delayDataset);
        chartPanel.add(new ChartPanel(barChart));

        // Top 2 catégories les plus signalées
        String[] columnNames = {"Statistique", "Valeur"};
        Object[][] data = {
          {"Top 1 catégorie d'incident la plus signalée", incidentTop1},
          {"Top 2 catégorie d'incident la plus signalée", incidentTop2},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable statsTable = new JTable(model);
        statsTable.setEnabled(false); 

        JScrollPane scrollPane = new JScrollPane(statsTable);
        chartPanel.add(scrollPane);

        // Tableau - Incidents urgents
        String[][] urgentsData = incidentUrgent.stream().map(i -> new String[]{
            String.valueOf(i.getIdTicket()),  
            i.getTitre(),
            i.getCategorie(),                   
            new SimpleDateFormat("dd/MM/yyyy").format(i.getDate_creation()) 
    }).toArray(String[][]::new);

    JTable tableUrgents = new JTable(new DefaultTableModel(urgentsData,
            new String[]{"ID", "Titre", "Catégorie", "Date"}));
    chartPanel.add(new JScrollPane(tableUrgents));
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}

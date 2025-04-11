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
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatMairieData;
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatMairieDatas;
import edu.ezip.ing1.pds.business.dto.Mairie;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Dashboard.StatMairieService;

public class MairieIHM extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private JDatePickerImpl datePickerStart;
    private JDatePickerImpl datePickerEnd;
    private JComboBox<String> codePostalComboBox;
    private JPanel chartPanel;
    private String codePostal;

    public MairieIHM() throws InterruptedException, IOException {
        setTitle("Statistiques sur les Mairies");
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

        datePanel.add(new JLabel("Date de debut:"));
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
        StatMairieService statMairieService = new StatMairieService(networkConfig);
        DashboardFilterDTO filterDTO = new DashboardFilterDTO(startDate, endDate, codePostal);

        StatMairieDatas statMairieDatas = statMairieService.mairie(filterDTO);

        if (statMairieDatas != null) {
            updateCharts(statMairieDatas);
        } else {
            JOptionPane.showMessageDialog(this, "Les données sont nulles.");
        }
        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner des dates valides.");
            return;
        }

    }
    @SuppressWarnings("unchecked")
    private void updateCharts(StatMairieDatas statMairieDatas) {
        chartPanel.removeAll();

    String incidentMairieTop1 = "", incidentMairieTop2 = "", incidentMairieTop3 = "";
    String suggestionMairieTop1 = "", suggestionMairieTop2 = "", suggestionMairieTop3 = "";
    int delaiMoyenIncident = 0, delaiMoyenSuggestion = 0;

    for (StatMairieData statMairieData : statMairieDatas.getStatMairieDataSet()) {
      
            incidentMairieTop1 += statMairieData.getIncidentMairieTop1();
            incidentMairieTop2 += statMairieData.getIncidentMairieTop2();
            incidentMairieTop3 += statMairieData.getIncidentMairieTop3();

            suggestionMairieTop1 += statMairieData.getSuggestionMairieTop1();
            suggestionMairieTop2 += statMairieData.getSuggestionMairieTop2();
            suggestionMairieTop3 += statMairieData.getSuggestionMairieTop3();

            delaiMoyenIncident += statMairieData.getDelaiIncidentMairie();
            delaiMoyenSuggestion += statMairieData.getDelaiSuggestionMairie();

        }

        // Délai moyen 
        @SuppressWarnings("rawtypes")
        DefaultCategoryDataset delayDataset = new DefaultCategoryDataset();
        delayDataset.addValue(delaiMoyenIncident, "Mairie", "Incident");
        delayDataset.addValue(delaiMoyenSuggestion, "Mairie", "Suggestion");
        JFreeChart barChart = ChartFactory.createBarChart("Delai Moyen de traitement par Mairie", "Catégorie", "Jours", delayDataset);
        chartPanel.add(new ChartPanel(barChart));

        // Top 3 mairies les plus signalées en matière d'incidents
        String[] columnNames = {"Statistique", "Valeur"};
        Object[][] data = {
          {"1ere Mairie en matière de nombre d'incidents signalés", incidentMairieTop1},
          {"2e Mairie en matière de nombre d'incidents signalés", incidentMairieTop2},
          {"3e Mairie en matière de nombre d'incidents signalés", incidentMairieTop3},
        };

        // Top 3 mairies les plus signalées en matière de suggestions
        String[] columnNames1 = {"Statistique", "Valeur"};
        Object[][] data1 = {
          {"1ere Mairie 1 en matière de nombre de suggestions reçues", suggestionMairieTop1},
          {"2e Mairie en matière de nombre de suggestions reçues", suggestionMairieTop2},
          {"3e Mairie en matière de nombre de suggestions reçues", suggestionMairieTop3},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable statsTable = new JTable(model);
        statsTable.setEnabled(false); 
        JScrollPane scrollPane = new JScrollPane(statsTable);
        chartPanel.add(scrollPane);


        DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
        JTable statsTable1 = new JTable(model1);
        statsTable.setEnabled(false); 
        JScrollPane scrollPane1 = new JScrollPane(statsTable1);
        chartPanel.add(scrollPane1);

        chartPanel.revalidate();
        chartPanel.repaint();
    }
}


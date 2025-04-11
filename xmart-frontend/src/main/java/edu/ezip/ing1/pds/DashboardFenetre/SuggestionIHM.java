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
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatSuggestionData;
import edu.ezip.ing1.pds.business.dto.DashboardDto.StatSuggestionDatas;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Dashboard.StatSuggestionService;

public class SuggestionIHM extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private JDatePickerImpl datePickerStart;
    private JDatePickerImpl datePickerEnd;
    private JComboBox<String> codePostalComboBox;
    private JPanel chartPanel;
    private String codePostal;

    public SuggestionIHM() throws InterruptedException, IOException {
        setTitle("Statistiques sur les Suggestions");
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
        StatSuggestionService statSuggestionService = new StatSuggestionService(networkConfig);
        DashboardFilterDTO filterDTO = new DashboardFilterDTO(startDate, endDate, codePostal);

        StatSuggestionDatas statSuggestionDatas = statSuggestionService.suggestion(filterDTO);

        if (statSuggestionDatas != null) {
            updateCharts(statSuggestionDatas);
        } else {
            JOptionPane.showMessageDialog(this, "Les données sont nulles.");
        }
        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner des dates valides.");
            return;
        }

    }
    @SuppressWarnings("unchecked")
    private void updateCharts(StatSuggestionDatas statSuggestionDatas) {
        chartPanel.removeAll();

    int suggestionNonResolu = 0, suggestionEnCours = 0, suggestionResolu = 0;
    double delaiVoirie = 0, delaiEclairagePublic = 0, delaiEspaceVerts = 0, delaiProprete = 0;
    double delaiAnimauxErrants = 0, delaiAutres = 0;
    String suggestionTop1 = "", suggestionTop2 = "";

    for (StatSuggestionData statSuggestionData : statSuggestionDatas.getStatSuggestionDataSet()) {
      
            suggestionNonResolu += statSuggestionData.getSuggestionNonVue();
            suggestionEnCours += statSuggestionData.getSuggestionEnCours();
            suggestionResolu += statSuggestionData.getSuggestionTraitee();

            delaiVoirie += statSuggestionData.getDelaiVoirie();
            delaiEclairagePublic += statSuggestionData.getDelaiEclairagePublic();
            delaiEspaceVerts += statSuggestionData.getDelaiEspaceVerts();
            delaiProprete += statSuggestionData.getDelaiProprete();
            delaiAnimauxErrants += statSuggestionData.getDelaiAnimauxErrants();
            delaiAutres  += statSuggestionData.getDelaiAutres();
        
            suggestionTop1 += statSuggestionData.getSuggestionTop1();
            suggestionTop2 += statSuggestionData.getSuggestionTop2();

        }

        // Répartition des incidents par statut
        @SuppressWarnings("rawtypes")
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Non Ouverts", suggestionNonResolu);
        pieDataset.setValue("En cours de traitement", suggestionEnCours);
        pieDataset.setValue("Résolu", suggestionResolu);
        JFreeChart pieChart = ChartFactory.createPieChart("Repartition des Suggestions par Statut", pieDataset, true, true, false);
        chartPanel.add(new ChartPanel(pieChart));

        // Délai moyen par catégorie
        @SuppressWarnings("rawtypes")
        DefaultCategoryDataset delayDataset = new DefaultCategoryDataset();
        delayDataset.addValue(delaiVoirie, "Suggestion", "Voirie");
        delayDataset.addValue(delaiEclairagePublic, "Suggestion", "Eclairage Public");
        delayDataset.addValue(delaiEspaceVerts, "Suggestion", "Espaces Verts");
        delayDataset.addValue(delaiProprete, "Suggestion", "Proprete");
        delayDataset.addValue(delaiAnimauxErrants, "Suggestion", "Animaux errants ou retrouvés morts");
        delayDataset.addValue(delaiAutres, "Suggestion", "Autres");

        JFreeChart barChart = ChartFactory.createBarChart("Delai Moyen de traitement par Catégorie", "Catégorie", "Jours", delayDataset);
        chartPanel.add(new ChartPanel(barChart));

        // Top 2 catégories les plus signalées
        String[] columnNames = {"Statistique", "Valeur"};
        Object[][] data = {
          {"Top 1 categorie de suggestion la plus signalée", suggestionTop1},
          {"Top 2 categorie de suggestion la plus signalée", suggestionTop2},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable statsTable = new JTable(model);
        statsTable.setEnabled(false); 

        JScrollPane scrollPane = new JScrollPane(statsTable);
        chartPanel.add(scrollPane);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}


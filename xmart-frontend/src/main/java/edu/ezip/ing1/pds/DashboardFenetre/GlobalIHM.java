package edu.ezip.ing1.pds.DashboardFenetre;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.business.dto.Mairie;
import edu.ezip.ing1.pds.business.dto.Mairies;
import edu.ezip.ing1.pds.business.dto.DashboardDto.DashboardFilterDTO;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalData;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalDatas;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Citoyen.MairieService;
import edu.ezip.ing1.pds.services.Dashboard.DashboardServiceGlobal;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class GlobalIHM extends JFrame {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";

    private JDatePickerImpl datePickerStart;
    private JDatePickerImpl datePickerEnd;

    private JComboBox<String> cpField;
    
    private JPanel chartPanel;

    private String codePostal;

    @SuppressWarnings("null")
    public GlobalIHM() throws InterruptedException, IOException {
        setTitle("Vue d'ensemble");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        initializeComponents();
        Date dateDebut = (Date) datePickerStart.getModel().getValue();
        Date dateFin = (Date) datePickerEnd.getModel().getValue();
        System.out.println("Date de début sélectionnée : " + datePickerStart.getModel().getValue());
        System.out.println("Date de fin sélectionnée : " + datePickerEnd.getModel().getValue());
        
        codePostal = (String) cpField.getSelectedItem();
        System.out.println("Code postal sélectionné : " + codePostal);

        NetworkConfig networkConfig= null;


        networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        DashboardFilterDTO filterDTO = new DashboardFilterDTO(dateDebut, dateFin, codePostal);
        
        DashboardServiceGlobal dashboardService = new DashboardServiceGlobal(networkConfig);
        GlobalDatas globalDatas = dashboardService.global(filterDTO);

        if (globalDatas != null) {
            updateCharts(globalDatas); 
        } else {
            System.out.println("Les données renvoyées par le backend sont nulles.");
        }
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
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

        try{
            NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
            final MairieService mairieService = new MairieService(networkConfig);
            Mairies mairies = mairieService.selectMairies();

            datePanel.add(new JLabel("Code Postal: "));
            cpField = new JComboBox<>();
            cpField.addItem("tout");
            for (Mairie mairie : mairies.getMairies()) {
                cpField.addItem(mairie.getCodePostal());
            }
            datePanel.add(cpField);

        } catch (IOException | InterruptedException e) {
            logger.error("Erreur lors du chargement des codes postaux", e);
        }


        JButton applyButton = new JButton("Appliquer");
        applyButton.addActionListener(e -> {
            System.out.println("Bouton Appliquer cliqué !");
            try {
                applyFilters();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
        });
        datePanel.add(applyButton);

        add(datePanel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 1));

        chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(2, 2));
        statsPanel.add(chartPanel);

        add(statsPanel, BorderLayout.CENTER);
    }

    private void applyFilters() throws IOException, InterruptedException {
        Date startDate = (Date) datePickerStart.getModel().getValue();
        Date endDate = (Date) datePickerEnd.getModel().getValue();
        codePostal = (String) cpField.getSelectedItem();
        //initializeComponents();
        System.out.println("Code postal sélectionné : " + codePostal);


        NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        DashboardFilterDTO filterDTO = new DashboardFilterDTO(startDate, endDate, codePostal);
        
        DashboardServiceGlobal dashboardService = new DashboardServiceGlobal(networkConfig);
        GlobalDatas globalDatas = dashboardService.global(filterDTO);


        if (globalDatas != null) {
            updateCharts(globalDatas); 
        } else {
            System.out.println("Les données renvoyées par le backend sont nulles.");
        }


        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner des dates valides.");
            return;
        }

        
    }

    @SuppressWarnings("unchecked")
    private void updateCharts(GlobalDatas globalDatas) {
        chartPanel.removeAll(); 

        int totalIncidents = 0; int totalSuggestions = 0;

        int incidentVoirie = 0, incidentEclairagePublic = 0, incidentEspaceVerts = 0, incidentProprete = 0;
        int incidentAnimauxErrants = 0, incidentAutres = 0;
        
        int suggestionVoirie = 0, suggestionEclairagePublic = 0, suggestionEspaceVerts = 0, suggestionProprete = 0;
        int suggestionAnimauxErrants = 0, suggestionAutres = 0;

        int incidentLevelBas = 0; int incidentLevelMoyen = 0; int incidentLevelHaut = 0; int incidentNonDefini = 0;
        
   
        for (GlobalData globalData : globalDatas.getGlobalDataSet()) {
      
            totalIncidents += globalData.getTotalIncidents();
            totalSuggestions += globalData.getTotalSuggestion();
          
            incidentVoirie += globalData.getIncidentCatVoirie();
            incidentEclairagePublic += globalData.getIncidentCatEclairagePublic();
            incidentEspaceVerts += globalData.getIncidentCatEspaceVerts();
            incidentProprete += globalData.getIncidentCatProprete();
            incidentAnimauxErrants += globalData.getIncidentCatAnimauxErrants();
            incidentAutres += globalData.getIncidentCatAutres();
        
            suggestionVoirie += globalData.getSuggestionCatVoirie();
            suggestionEclairagePublic += globalData.getSuggestionCatEclairagePublic();
            suggestionEspaceVerts += globalData.getSuggestionCatEspaceVerts();
            suggestionProprete += globalData.getSuggestionCatProprete();
            suggestionAnimauxErrants += globalData.getSuggestionCatAnimauxErrants();
            suggestionAutres += globalData.getSuggestionCatAutres();

            incidentLevelBas += globalData.getIncidentLevelBas();
            incidentLevelMoyen += globalData.getIncidentLevelMoyen();
            incidentLevelHaut += globalData.getIncidentLevelHaut();
            incidentNonDefini += globalData.getIncidentNonDefini();
        }

        String[] columnNames = {"Statistique", "Valeur"};
        Object[][] data = {
            {"Nombre total d'incidents", totalIncidents},
            {"Nombre total de suggestions", totalSuggestions}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable statsTable = new JTable(model);
        statsTable.setEnabled(false);
        
        JScrollPane scrollPane = new JScrollPane();
        statsTable.add(scrollPane);
        chartPanel.add(statsTable);
    
        @SuppressWarnings("rawtypes")
        DefaultPieDataset incidentDataset = new DefaultPieDataset();
        incidentDataset.setValue("Voirie", incidentVoirie);
        incidentDataset.setValue("Eclairage Public", incidentEclairagePublic);
        incidentDataset.setValue("Espace Vert", incidentEspaceVerts);
        incidentDataset.setValue("Propreté", incidentProprete);
        incidentDataset.setValue("Animaux Errants", incidentAnimauxErrants);
        incidentDataset.setValue("Autres", incidentAutres);
        JFreeChart incidentChart = ChartFactory.createPieChart("Repartition des Incidents par Catégorie", incidentDataset, true, true, false);
        ChartPanel incidentChartPanel = new ChartPanel(incidentChart);
        chartPanel.add(incidentChartPanel);
    
        @SuppressWarnings("rawtypes")
        DefaultPieDataset suggestionDataset = new DefaultPieDataset();
        suggestionDataset.setValue("Voirie", suggestionVoirie);
        suggestionDataset.setValue("Eclairage Public", suggestionEclairagePublic);
        suggestionDataset.setValue("Espace Vert", suggestionEspaceVerts);
        suggestionDataset.setValue("Propreté", suggestionProprete);
        suggestionDataset.setValue("Animaux Errants", suggestionAnimauxErrants);
        suggestionDataset.setValue("Autres", suggestionAutres);
        JFreeChart suggestionChart = ChartFactory.createPieChart("Repartition des Suggestions par Catégorie", suggestionDataset, true, true, false);
        ChartPanel suggestionChartPanel = new ChartPanel(suggestionChart);
        chartPanel.add(suggestionChartPanel);

        DefaultCategoryDataset priorityDataset = new DefaultCategoryDataset();
        priorityDataset.addValue(incidentLevelBas, "Incident", "Basse");
        priorityDataset.addValue(incidentLevelMoyen, "Incident", "Moyenne");
        priorityDataset.addValue(incidentLevelHaut, "Incident", "Haute");
        priorityDataset.addValue(incidentNonDefini, "Incident", "Non défini");
        JFreeChart priorityChart = ChartFactory.createBarChart("Repartition des Incidents par Priorité", "Priorité", "Nombre", priorityDataset);
        ChartPanel priorityChartPanel = new ChartPanel(priorityChart);
        chartPanel.add(priorityChartPanel);
    
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
}

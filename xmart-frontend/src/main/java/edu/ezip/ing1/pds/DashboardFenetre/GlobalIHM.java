package edu.ezip.ing1.pds.DashboardFenetre;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.business.dto.DashboardDto.DashboardFilterDTO;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalData;
import edu.ezip.ing1.pds.business.dto.DashboardDto.GlobalDatas;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
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

    // Composants pour la sélection de date
    private JDatePickerImpl datePickerStart;
    private JDatePickerImpl datePickerEnd;

    // Récupérer les dates sélectionnées dans les JDatePickerImpl
    Date startDate = (Date) datePickerStart.getModel().getValue();
    Date endDate = (Date) datePickerEnd.getModel().getValue();

    // Composant pour la sélection du code postal
    private JComboBox<String> codePostalComboBox;
    
    // Composants graphiques
    private JPanel chartPanel;

    // Variable pour stocker le code postal
    private String codePostal;

    public GlobalIHM() throws InterruptedException, IOException {
        setTitle("Dashboard Global");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeComponents();
        setVisible(true);
        // Charger la configuration du réseau
        NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        // Créer l'objet de filtre avec les paramètres
        DashboardFilterDTO filterDTO = new DashboardFilterDTO(startDate, endDate, codePostal);
        
        // Appeler le service global pour obtenir les données filtrées
        DashboardServiceGlobal dashboardService = new DashboardServiceGlobal(networkConfig);
        GlobalDatas globalDatas = dashboardService.global(filterDTO);
        try {
            globalDatas = dashboardService.global(filterDTO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
        // Mettre à jour les graphiques
        updateCharts(globalDatas);
    }

    private void initializeComponents() {
        // Initialisation des modèles de date
        UtilDateModel modelStart = new UtilDateModel();
        UtilDateModel modelEnd = new UtilDateModel();
        
        JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, null);
        JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, null);

        datePickerStart = new JDatePickerImpl(datePanelStart, null);
        datePickerEnd = new JDatePickerImpl(datePanelEnd, null);

        // Panel de sélection des dates
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout());

        datePanel.add(new JLabel("Date de début:"));
        datePanel.add(datePickerStart);

        datePanel.add(new JLabel("Date de fin:"));
        datePanel.add(datePickerEnd);

        // ComboBox pour la sélection du code postal
        JLabel codePostalLabel = new JLabel("Code Postal:");
        codePostalComboBox = new JComboBox<>(new String[] {"75000", "75001", "75002"});  // Ajoutez les codes postaux nécessaires
        datePanel.add(codePostalLabel);
        datePanel.add(codePostalComboBox);

        // Bouton appliquer les filtres
        JButton applyButton = new JButton("Appliquer");
        applyButton.addActionListener(e -> {
            try {
                applyFilters();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
        });
        datePanel.add(applyButton);

        // Ajouter le panel des dates à la fenêtre
        add(datePanel, BorderLayout.NORTH);

        // Section de statistiques
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 1));

        // Graphiques (camemberts et histogrammes)
        chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(2, 2));
        statsPanel.add(chartPanel);

        // Ajouter le panel des statistiques à la fenêtre
        add(statsPanel, BorderLayout.CENTER);
    }

    private void applyFilters() throws IOException, InterruptedException {
        Date startDate = (Date) datePickerStart.getModel().getValue();
        Date endDate = (Date) datePickerEnd.getModel().getValue();
        codePostal = (String) codePostalComboBox.getSelectedItem();  // Récupérer le code postal sélectionné

        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner des dates valides.");
            return;
        }

        
    }

    @SuppressWarnings("unchecked")
    private void updateCharts(GlobalDatas globalDatas) {
        chartPanel.removeAll();  // Vider le panel avant d'ajouter de nouveaux graphiques
        //statsPanel.removeAll();

        // Initialisation des variables pour l'agrégation des données

        int totalIncidents = 0; int totalSuggestions = 0;

        int incidentVoirie = 0, incidentEclairagePublic = 0, incidentEspaceVerts = 0, incidentProprete = 0;
        int incidentAnimauxErrants = 0, incidentAutres = 0;
        
        int suggestionVoirie = 0, suggestionEclairagePublic = 0, suggestionEspaceVerts = 0, suggestionProprete = 0;
        int suggestionAnimauxErrants = 0, suggestionAutres = 0;

        int incidentLevelBas = 0; int incidentLevelMoyen = 0; int incidentLevelHaut = 0; int incidentNonDefini = 0;
        
        // Itération sur tous les objets GlobalData dans GlobalDatas
        for (GlobalData globalData : globalDatas.getGlobalDataSet()) {
            // Agregation pour totaux
            totalIncidents += globalData.getTotalIncidents();
            totalSuggestions += globalData.getTotalSuggestion();
            // Agrégation des incidents
            incidentVoirie += globalData.getIncidentCatVoirie();
            incidentEclairagePublic += globalData.getIncidentCatEclairagePublic();
            incidentEspaceVerts += globalData.getIncidentCatEspaceVerts();
            incidentProprete += globalData.getIncidentCatProprete();
            incidentAnimauxErrants += globalData.getIncidentCatAnimauxErrants();
            incidentAutres += globalData.getIncidentCatAutres();
            
            // Agrégation des suggestions
            suggestionVoirie += globalData.getSuggestionCatVoirie();
            suggestionEclairagePublic += globalData.getSuggestionCatEclairagePublic();
            suggestionEspaceVerts += globalData.getSuggestionCatEspaceVerts();
            suggestionProprete += globalData.getSuggestionCatProprete();
            suggestionAnimauxErrants += globalData.getSuggestionCatAnimauxErrants();
            suggestionAutres += globalData.getSuggestionCatAutres();

            // Agregation pour incident par priorité
            incidentLevelBas += globalData.getIncidentLevelBas();
            incidentLevelMoyen += globalData.getIncidentLevelMoyen();
            incidentLevelHaut += globalData.getIncidentLevelHaut();
            incidentNonDefini += globalData.getIncidentNonDefini();
        }

        // Mise à jour du tableau pour les totaux
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
    
        // Mise à jour du graphique camembert pour les incidents
        @SuppressWarnings("rawtypes")
        DefaultPieDataset incidentDataset = new DefaultPieDataset();
        incidentDataset.setValue("Voirie", incidentVoirie);
        incidentDataset.setValue("Eclairage Public", incidentEclairagePublic);
        incidentDataset.setValue("Espace Vert", incidentEspaceVerts);
        incidentDataset.setValue("Propreté", incidentProprete);
        incidentDataset.setValue("Animaux Errants", incidentAnimauxErrants);
        incidentDataset.setValue("Autres", incidentAutres);
        JFreeChart incidentChart = ChartFactory.createPieChart("Répartition des Incidents par Catégorie", incidentDataset, true, true, false);
        ChartPanel incidentChartPanel = new ChartPanel(incidentChart);
        chartPanel.add(incidentChartPanel);
    
        // Mise à jour du graphique camembert pour les suggestions
        @SuppressWarnings("rawtypes")
        DefaultPieDataset suggestionDataset = new DefaultPieDataset();
        suggestionDataset.setValue("Voirie", suggestionVoirie);
        suggestionDataset.setValue("Eclairage Public", suggestionEclairagePublic);
        suggestionDataset.setValue("Espace Vert", suggestionEspaceVerts);
        suggestionDataset.setValue("Propreté", suggestionProprete);
        suggestionDataset.setValue("Animaux Errants", suggestionAnimauxErrants);
        suggestionDataset.setValue("Autres", suggestionAutres);
        JFreeChart suggestionChart = ChartFactory.createPieChart("Répartition des Suggestions par Catégorie", suggestionDataset, true, true, false);
        ChartPanel suggestionChartPanel = new ChartPanel(suggestionChart);
        chartPanel.add(suggestionChartPanel);
    
        // Mise à jour de l'histogramme pour les incidents par niveau de priorité
        DefaultCategoryDataset priorityDataset = new DefaultCategoryDataset();
        priorityDataset.addValue(incidentLevelBas, "Incident", "Basse");
        priorityDataset.addValue(incidentLevelMoyen, "Incident", "Moyenne");
        priorityDataset.addValue(incidentLevelHaut, "Incident", "Haute");
        priorityDataset.addValue(incidentNonDefini, "Incident", "Non défini");
        JFreeChart priorityChart = ChartFactory.createBarChart("Répartition des Incidents par Priorité", "Priorité", "Nombre", priorityDataset);
        ChartPanel priorityChartPanel = new ChartPanel(priorityChart);
        chartPanel.add(priorityChartPanel);
    
        // Repeindre le panel des graphiques
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new GlobalIHM();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
}

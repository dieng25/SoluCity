package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteSuggestion;

import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Suggestion;
import edu.ezip.ing1.pds.services.Citoyen.Connexion.AccueilConnexion;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class SuggestionConsulte extends JFrame {

    private JComboBox<String> cpComboBox;
    private JComboBox<String> categorieComboBox;
    private JComboBox<String> statutComboBox;
    private TableRowSorter<TableModel> sorter;

    public SuggestionConsulte(List<Suggestion> suggestions, Citoyen citoyen) {
        setTitle("Consulter les Suggestions");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Ticket Suggestions", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        TableModel model = new SuggestionTableModel(suggestions);
        JTable table = new JTable(model);
        sorter = new TableRowSorter(model);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        int modelRow = table.convertRowIndexToModel(row);
                        Suggestion suggestion = suggestions.get(modelRow);
                        new ShowSuggestionDetails(suggestion);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Filtre CP
        cpComboBox = new JComboBox<>();
        cpComboBox.addItem("Tous");
        suggestions.stream().map(Suggestion::getCP_Ticket).distinct().forEach(cpComboBox::addItem);
        cpComboBox.addActionListener(e -> filterTable());
        filterPanel.add(new JLabel("Code Postal:"));
        filterPanel.add(cpComboBox);

        // Filtre Catégorie
        categorieComboBox = new JComboBox<>();
        categorieComboBox.addItem("Tous");
        suggestions.stream().map(Suggestion::getCategorie).distinct().forEach(categorieComboBox::addItem);
        categorieComboBox.addActionListener(e -> filterTable());
        filterPanel.add(new JLabel("Catégorie:"));
        filterPanel.add(categorieComboBox);

        // Filtre Statut
        statutComboBox = new JComboBox<>();
        statutComboBox.addItem("Tous");
        statutComboBox.addItem("Ouvert");
        statutComboBox.addItem("En cours de traitement");
        statutComboBox.addItem("Accepté");
        statutComboBox.addItem("Refusé");
        statutComboBox.addActionListener(e -> filterTable());
        filterPanel.add(new JLabel("Statut:"));
        filterPanel.add(statutComboBox);

        mainPanel.add(filterPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new AccueilConnexion(citoyen);
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void filterTable() {
        RowFilter<TableModel, Object> filter = new RowFilter<TableModel, Object>() {
            public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                String cp = cpComboBox.getSelectedItem().toString();
                String categorie = categorieComboBox.getSelectedItem().toString();
                String statut = statutComboBox.getSelectedItem().toString();

                boolean cpMatch = cp.equals("Tous") || entry.getStringValue(1).equals(cp);
                boolean categorieMatch = categorie.equals("Tous") || entry.getStringValue(2).equals(categorie);
                boolean statutMatch = statut.equals("Tous") || entry.getStringValue(5).equals(statut);

                return cpMatch && categorieMatch && statutMatch;
            }
        };
        sorter.setRowFilter(filter);
    }

}

package edu.ezip.ing1.pds.services.Citoyen.Connexion.ConsulteSuggestion;

import edu.ezip.ing1.pds.business.dto.Suggestion;

import javax.swing.*;
import java.awt.*;

public class ShowSuggestionDetails extends JFrame {

    public ShowSuggestionDetails(Suggestion suggestion) {

        setTitle("Détails de la Suggestion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel descriptionPanel = new JPanel(new BorderLayout());
        JLabel descriptionLabel = new JLabel("Description :");
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(suggestion.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPaneDescription = new JScrollPane(descriptionArea);
        scrollPaneDescription.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        descriptionPanel.add(scrollPaneDescription, BorderLayout.CENTER);

        mainPanel.add(descriptionPanel, BorderLayout.NORTH);

        JPanel commentairePanel = new JPanel(new BorderLayout());
        JLabel commentaireLabel = new JLabel("Commentaire :");
        commentaireLabel.setFont(new Font("Arial", Font.BOLD, 14));
        commentairePanel.add(commentaireLabel, BorderLayout.NORTH);

        JTextArea commentaireArea = new JTextArea(suggestion.getCommentaire());
        commentaireArea.setEditable(false);
        commentaireArea.setLineWrap(true);
        commentaireArea.setWrapStyleWord(true);
        commentaireArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPaneCommentaire = new JScrollPane(commentaireArea);
        scrollPaneCommentaire.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        commentairePanel.add(scrollPaneCommentaire, BorderLayout.CENTER);

        mainPanel.add(commentairePanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);



    }

}

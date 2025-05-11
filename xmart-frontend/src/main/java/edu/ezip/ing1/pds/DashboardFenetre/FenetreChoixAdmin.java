package edu.ezip.ing1.pds.DashboardFenetre;
import javax.swing.*;
import java.awt.*;

public class FenetreChoixAdmin extends JFrame{

    public FenetreChoixAdmin() {

        setTitle("Page Administrateur");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);


        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.WHITE);
        backgroundPanel.setLayout(new BorderLayout());


        JLabel welcomeLabel = new JLabel("Bienvenue Admin!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0, 123, 255));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        backgroundPanel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton Continuer = new JButton("Mes tableaux de bord");
        Continuer.addActionListener(e -> openMainDashboard());
        JButton MajInfoAdmin = new JButton("Maj infos admin");
        MajInfoAdmin.addActionListener(e -> openMajAdmin());
        JButton AddUser = new JButton("Ajouter un utilisateur");
        AddUser.addActionListener(e -> openCreationUser());
        JButton MajInfoUser = new JButton("Maj infos utilisateurs");
        MajInfoUser.addActionListener(e -> openMajUser());
        
        
        JButton[] buttons = {Continuer, MajInfoAdmin, AddUser, MajInfoUser};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        buttonPanel.add(Continuer);
        buttonPanel.add(MajInfoAdmin);
        buttonPanel.add(AddUser);
        buttonPanel.add(MajInfoUser);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        add(backgroundPanel, BorderLayout.CENTER);

    }

    private void openMainDashboard() {
        SwingUtilities.invokeLater(() -> {
                    new MainDashboard().setVisible(true);
           
        });
    }

    private void openMajAdmin() {
        SwingUtilities.invokeLater(() -> { 
            new UpdateAdmin().setVisible(true);
        });
    }

    private void openCreationUser() {
        SwingUtilities.invokeLater(() -> {
                    new CreationUtilisateur().setVisible(true);
        });
    }

    private void openMajUser() {
        SwingUtilities.invokeLater(() -> { new UpdateUtilisateur().setVisible(true);
        });
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainDashboard().setVisible(true);
        });
    }
}
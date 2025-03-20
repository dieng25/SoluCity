package edu.ezip.ing1.pds.services.Citoyen;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConfirmeExit extends JFrame {

    public ConfirmeExit() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });

        setTitle("Fenêtre avec Confirmation");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void confirmExit() {
        int response = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment quitter l'application ?", "Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}

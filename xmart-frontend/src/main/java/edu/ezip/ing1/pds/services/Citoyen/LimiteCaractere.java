package edu.ezip.ing1.pds.services.Citoyen;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimiteCaractere extends PlainDocument {

    private int max;

    public LimiteCaractere(int max) {
        super();
        this.max = max;
    }
    public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text==null)
            return;
        if ((getLength() + text.length()) <= max) {
            super.insertString(offset, text, attr);
        }
        else {
            JOptionPane.showMessageDialog(null, "Limite de " + max + " caractères atteinte !", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }
}

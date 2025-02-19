package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "citoyen")
public class Citoyen {
    private String telNum;
    private String nom;
    private String prenom;
    private String email;
    private String identifiant;

    public Citoyen () {
    }

    public Citoyen(String telNum, String nom, String prenom, String email, String identifiant){
        this.telNum = telNum;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.identifiant = identifiant;
    }

    public String getTelNum() {
        return telNum;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    @JsonProperty("citoyen_telNum")
    public void setTelNum(String telNum) {
        if (telNum == null || !telNum.matches("^0\\d{9}$")) {
            throw new IllegalArgumentException("Le numéro de téléphone doit commencer par 0 et contenir exactement 10 chiffres.");
        }
        this.telNum = telNum;
    }

    @JsonProperty("citoyen_nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonProperty("citoyen_prenom")
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @JsonProperty("citoyen_email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("citoyen_identifiant")
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public String toString() {
        return "Citoyen [telNum=" + telNum + ", " +
                "nom=" + nom +
                ", prenom=" + prenom +
                ", email=" + email +
                ", identifiant=" + identifiant + "]";
    }
}


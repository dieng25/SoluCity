package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardData {
    
    @JsonProperty("total_incident")
    private int totalIncident;
    
    @JsonProperty("incident_en_cours")
    private int incidentEnCours;
    
    @JsonProperty("incident_resolu")
    private int incidentResolu;
    
    @JsonProperty("incident_non_ouvert")
    private int incidentNonOuvert;
    
    @JsonProperty("non_defini")
    private int nonDefini;
    
    @JsonProperty("faible")
    private int faible;
    
    @JsonProperty("moyen")
    private int moyen;
    
    @JsonProperty("haut")
    private int haut;

    public DashboardData() {
    }

    // Getters et Setters

    public int getTotalIncident() {
        return totalIncident;
    }

    public void setTotalIncident(int totalIncident) {
        this.totalIncident = totalIncident;
    }

    public int getIncidentEnCours() {
        return incidentEnCours;
    }

    public void setIncidentEnCours(int incidentEnCours) {
        this.incidentEnCours = incidentEnCours;
    }

    public int getIncidentResolu() {
        return incidentResolu;
    }

    public void setIncidentResolu(int incidentResolu) {
        this.incidentResolu = incidentResolu;
    }

    public int getIncidentNonOuvert() {
        return incidentNonOuvert;
    }

    public void setIncidentNonOuvert(int incidentNonOuvert) {
        this.incidentNonOuvert = incidentNonOuvert;
    }

    public int getNonDefini() {
        return nonDefini;
    }

    public void setNonDefini(int nonDefini) {
        this.nonDefini = nonDefini;
    }

    public int getFaible() {
        return faible;
    }

    public void setFaible(int faible) {
        this.faible = faible;
    }

    public int getMoyen() {
        return moyen;
    }

    public void setMoyen(int moyen) {
        this.moyen = moyen;
    }

    public int getHaut() {
        return haut;
    }

    public void setHaut(int haut) {
        this.haut = haut;
    }

    @Override
    public String toString() {
        return "DashboardData{" +
                "totalIncident=" + totalIncident +
                ", incidentEnCours=" + incidentEnCours +
                ", incidentResolu=" + incidentResolu +
                ", incidentNonOuvert=" + incidentNonOuvert +
                ", nonDefini=" + nonDefini +
                ", faible=" + faible +
                ", moyen=" + moyen +
                ", haut=" + haut +
                '}';
    }
}

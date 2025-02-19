package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class Incidents {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("incidents")
    private Set<Incident> incidents = new LinkedHashSet<>();

    // Constructeur par défaut
    public Incidents() {
    }

    // Getter
    public Set<Incident> getIncidents() {
        return incidents;
    }

    // Setter
    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }

    // Ajouter un incident à la liste
    public final Incidents add(final Incident incident) {
        incidents.add(incident);
        return this;
    }

    @Override
    public String toString() {
        return "Incidents{" +
                "incidents=" + incidents +
                '}';
    }
}

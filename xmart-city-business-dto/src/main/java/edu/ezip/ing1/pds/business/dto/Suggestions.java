package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class Suggestions {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("suggestions")
    private Set<Suggestion> suggestions = new LinkedHashSet<>();

    // Constructeur par défaut
    public Suggestions() {
    }

    // Getter
    public Set<Suggestion> getIncidents() {
        return suggestions;
    }

    // Setter
    public void setIncidents(Set<Incident> incidents) {
        this.suggestions = suggestions;
    }

    // Ajouter un incident à la liste
    public final Suggestions add(final Suggestion suggestion) {
        suggestions.add(suggestion);
        return this;
    }

    @Override
    public String toString() {
        return "Incidents{" +
                "incidents=" + suggestions +
                '}';
    }
}

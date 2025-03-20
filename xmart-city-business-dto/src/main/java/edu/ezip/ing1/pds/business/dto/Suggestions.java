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
    public Set<Suggestion> getSuggestions() {
        return suggestions;
    }

    // Setter
    public void setSuggestions(Set<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    // Ajouter une sugg à la liste
    public final Suggestions add(final Suggestion suggestion) {
        suggestions.add(suggestion);
        return this;
    }

    @Override
    public String toString() {
        return "Suggestions{" +
                "suggestions=" + suggestions +
                '}';
    }
}

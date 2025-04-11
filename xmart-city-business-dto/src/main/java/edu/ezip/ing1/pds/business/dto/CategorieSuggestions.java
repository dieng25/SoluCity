package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class CategorieSuggestions {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("categorieSuggestions")
    private Set<CategorieSuggestion> categorieSuggestions = new LinkedHashSet<CategorieSuggestion>();

    public Set<CategorieSuggestion> getCategorieSuggestions() {
        return categorieSuggestions;
    }

    public void setCategorieSuggestions(Set<CategorieSuggestion> categorieSuggestions) {
        this.categorieSuggestions = categorieSuggestions;
    }

    public final CategorieSuggestions add (final CategorieSuggestion categorieSuggestion) {
        categorieSuggestions.add(categorieSuggestion);
        return this;
    }

    @Override
    public String toString() {
        return "CategorieSuggestions{" +
                "categorieSuggestions = " + categorieSuggestions +
                '}';
    }
}

package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class CategorieIncidents {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("categorieIncidents")
    private Set<CategorieIncident> categorieIncidents = new LinkedHashSet<CategorieIncident>();

    public Set<CategorieIncident> getCategorieIncidents() {
        return categorieIncidents;
    }

    public void setCategorieIncidents(Set<CategorieIncident> categorieIncidents) {
        this.categorieIncidents = categorieIncidents;
    }

    public final CategorieIncidents add (final CategorieIncident categorieIncident) {
        categorieIncidents.add(categorieIncident);
        return this;
    }

    @Override
    public String toString() {
        return "CategorieIncidents{" +
                "categorieIncidents = " + categorieIncidents +
                '}';
    }
}

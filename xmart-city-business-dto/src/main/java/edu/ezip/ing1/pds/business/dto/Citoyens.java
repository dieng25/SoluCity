package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class Citoyens {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("citoyens")
    private Set<Citoyen> citoyens = new LinkedHashSet<Citoyen>();

    public Set<Citoyen> getCitoyens() {
        return citoyens;
    }

    public void setCitoyens(Set<Citoyen> citoyens) {
        this.citoyens = citoyens;
    }

    public final Citoyens add (final Citoyen citoyen) {
        citoyens.add(citoyen);
        return this;
    }

    @Override
    public String toString() {
        return "Citoyens{" +
                "citoyens=" + citoyens +
                '}';
    }
}
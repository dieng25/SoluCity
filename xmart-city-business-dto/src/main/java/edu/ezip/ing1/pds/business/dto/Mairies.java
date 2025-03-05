package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class Mairies {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("mairies")
    private Set<Mairie> mairies = new LinkedHashSet<Mairie>();

    public Set<Mairie> getMairies() {
        return mairies;
    }

    public void setMairies(Set<Mairie> mairies) {
        this.mairies = mairies;
    }

    public final Mairies add (final Mairie mairie) {
        mairies.add(mairie);
        return this;
    }

    @Override
    public String toString() {
        return "Mairies{" +
                "mairies=" + mairies +
                '}';
    }
}

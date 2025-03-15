package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class StatIncidentDatas {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("statIncidentDatas")
    private Set<StatIncidentData> statIncidentDatas = new LinkedHashSet<>();

    public Set<StatIncidentData> getStatIncidentDataSet() {
        return statIncidentDatas;
    }

    public void setStatIncidentDataSet(Set<StatIncidentData> statIncidentDatas) {
        this.statIncidentDatas = statIncidentDatas;
    }

    public final StatIncidentDatas add(final StatIncidentData statIncidentData) {
        statIncidentDatas.add(statIncidentData);
        return this;
    }

    @Override
    public String toString() {
        return "StatIncidentDatas{" +
                "statIncidentDatas=" + statIncidentDatas +
                '}';
    }
}

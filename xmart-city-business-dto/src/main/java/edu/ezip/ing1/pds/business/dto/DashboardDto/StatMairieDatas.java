package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class StatMairieDatas {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("statMairieDatas")
    private Set<StatMairieData> statMairieDatas = new LinkedHashSet<>();

    public Set<StatMairieData> getStatMairieDataSet() {
        return statMairieDatas;
    }

    public void setStatMairieDataSet(Set<StatMairieData> statMairieDatas) {
        this.statMairieDatas = statMairieDatas;
    }

    public final StatMairieDatas add(final StatMairieData statMairieData) {
        statMairieDatas.add(statMairieData);
        return this;
    }

    @Override
    public String toString() {
        return "StatMairieDatas{" +
                "statMairieDatas=" + statMairieDatas +
                '}';
    }
}


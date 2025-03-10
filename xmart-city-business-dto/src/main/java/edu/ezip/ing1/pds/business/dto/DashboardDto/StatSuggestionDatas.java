package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class StatSuggestionDatas {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("statSuggestionDatas")
    private Set<StatSuggestionData> statSuggestionDatas = new LinkedHashSet<>();

    public Set<StatSuggestionData> getStatSuggestionDataSet() {
        return statSuggestionDatas;
    }

    public void setStatSuggestionDataSet(Set<StatSuggestionData> statSuggestionDatas) {
        this.statSuggestionDatas = statSuggestionDatas;
    }

    public final StatSuggestionDatas add(final StatSuggestionData statSuggestionData) {
        statSuggestionDatas.add(statSuggestionData);
        return this;
    }

    @Override
    public String toString() {
        return "StatSuggestionDatas{" +
                "statSuggestionDatas=" + statSuggestionDatas +
                '}';
    }
}

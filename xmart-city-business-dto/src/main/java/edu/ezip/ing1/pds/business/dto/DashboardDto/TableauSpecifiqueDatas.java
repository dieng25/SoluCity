package edu.ezip.ing1.pds.business.dto.DashboardDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class TableauSpecifiqueDatas {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tableauSpecifiqueDatas")
    private Set<TableauSpecifiqueData> tableauSpecifiqueDatas = new LinkedHashSet<>();

    public Set<TableauSpecifiqueData> getTableauSpecifiqueDataSet() {
        return tableauSpecifiqueDatas;
    }

    public void setTableauSpecifiqueDataSet(Set<TableauSpecifiqueData> tableauSpecifiqueDatas) {
        this.tableauSpecifiqueDatas = tableauSpecifiqueDatas;
    }

    public final TableauSpecifiqueDatas add(final TableauSpecifiqueData tableauSpecifiqueData) {
        tableauSpecifiqueDatas.add(tableauSpecifiqueData);
        return this;
    }

    @Override
    public String toString() {
        return "TableauSpecifiqueDatas{" +
                "tableauSpecifiqueDatas=" + tableauSpecifiqueDatas +
                '}';
    }
}


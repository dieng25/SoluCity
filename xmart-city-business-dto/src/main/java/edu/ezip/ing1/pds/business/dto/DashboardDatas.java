package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.LinkedHashSet;
import java.util.Set;

@JsonRootName("dashboardDatas")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardDatas {

    @JsonProperty("dashboard_data")
    private Set<DashboardData> dashboardDataSet = new LinkedHashSet<>();

    public Set<DashboardData> getDashboardDataSet() {
        return dashboardDataSet;
    }

    public void setDashboardDataSet(Set<DashboardData> dashboardDataSet) {
        this.dashboardDataSet = dashboardDataSet;
    }

    public final DashboardDatas add(final DashboardData dashboardData) {
        dashboardDataSet.add(dashboardData);
        return this;
    }

    @Override
    public String toString() {
        return "DashboardDatas{" +
                "dashboardDataSet=" + dashboardDataSet +
                '}';
    }
}


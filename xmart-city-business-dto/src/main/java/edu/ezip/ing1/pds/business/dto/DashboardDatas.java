package edu.ezip.ing1.pds.business.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashSet;
import java.util.Set;



public class DashboardDatas {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dashboardDatas")
    private Set<DashboardData> dashboardDatas = new LinkedHashSet<>();

    public Set<DashboardData> getDashboardDataSet() {
        return dashboardDatas;
    }

    public void setDashboardDataSet(Set<DashboardData> dashboardDatas) {
        this.dashboardDatas = dashboardDatas;
    }

    public final DashboardDatas add(final DashboardData dashboardData) {
        dashboardDatas.add(dashboardData);
        return this;
    }

    @Override
    public String toString() {
        return "DashboardDatas{" +
                "dashboardDatas=" + dashboardDatas +
                '}';
    }
}


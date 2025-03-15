package edu.ezip.ing1.pds.business.dto.DashboardDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashSet;
import java.util.Set;

public class GlobalDatas {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("globalDatas")
    private Set<GlobalData> globalDatas = new LinkedHashSet<>();

    public Set<GlobalData> getGlobalDataSet() {
        return globalDatas;
    }

    public void setGlobalDataSet(Set<GlobalData> globalDatas) {
        this.globalDatas = globalDatas;
    }

    public final GlobalDatas add(final GlobalData globalData) {
        globalDatas.add(globalData);
        return this;
    }

    @Override
    public String toString() {
        return "GlobalDatas{" +
                "globalDatas=" + globalDatas +
                '}';
    }
}


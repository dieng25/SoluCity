package edu.ezip.ing1.pds.business.dto.DashboardDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "dashboardfilterdto")
public class DashboardFilterDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateDebut;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateFin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String codePostal;

    public DashboardFilterDTO() {}

    public DashboardFilterDTO(Date dateDebut, Date dateFin, String codePostal) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.codePostal = codePostal;
    }


    public Date getDateDebut() {
        return dateDebut;
    }
    @JsonProperty("date_debut")
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    @JsonProperty("date_fin")
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    
    public String getCodePostal() {
        return codePostal;
    }

    @JsonProperty("code_postal")
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        return "DashboardFilterDTO{" +
                "dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", codePostal='" + codePostal + '\'' +
                '}';
    }
}

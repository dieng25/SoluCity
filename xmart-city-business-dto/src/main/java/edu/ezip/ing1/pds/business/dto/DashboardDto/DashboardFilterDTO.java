package edu.ezip.ing1.pds.business.dto.DashboardDto;

import java.util.Date;

public class DashboardFilterDTO {
    
    private Date dateDebut;
    private Date dateFin;
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

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getCodePostal() {
        return codePostal;
    }

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

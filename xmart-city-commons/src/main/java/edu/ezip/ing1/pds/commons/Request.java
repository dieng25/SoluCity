package edu.ezip.ing1.pds.commons;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import java.sql.Date;

@JsonRootName(value = "request")
public class Request {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String requestId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestOrder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String requestBody = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dateDebut;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date dateFin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String codePostal;

    @JsonProperty("request_order")
    public void setRequestOrder(String requestOrder) {
        this.requestOrder = requestOrder;
    }

    @JsonSetter("request_body")
    public void setRequestBody(JsonNode requestBody) {
        this.requestBody = requestBody.toString();
    }
    public void setRequestContent(final String requestBody) {
        this.requestBody = requestBody;
    }

    @JsonProperty("request_id")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getRequestId() {
        return requestId;
    }

    public String getRequestOrder() {
        return requestOrder;
    }

    @JsonRawValue
    public String getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "{\"request\": {" +
                "\"request_id\":\"" + requestId + "\"," +
                "\"request_order\":\"" + requestOrder + "\"," +
                "\"request_body\":\"" + requestBody + "\"," +
                "\"date_debut\":\"" + dateDebut + "\"," +
                "\"date_fin\":\"" + dateFin + "\"," +
                "\"code_postal\":\"" + codePostal + "\"" +
                "}}";
    }
}

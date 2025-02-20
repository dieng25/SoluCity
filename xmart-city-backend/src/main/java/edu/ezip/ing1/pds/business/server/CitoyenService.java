package edu.ezip.ing1.pds.business.server;


import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class CitoyenService {

    private enum Queries {
        INSERT_CITOYEN("INSERT INTO Citoyen (tel_num, Nom, Prénom, email, Identifiant) VALUES (?, ?, ?, ?, ?)"),
        INSERT_INCIDENT("INSERT INTO Incident (Titre, Description, date_emis, Catégorie, Statut, CodePostal_ticket, Priorité, tel_num) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"),
        INSERT_SUGGESTION("INSERT INTO Suggestion (Titre, Description, date_emis, Catégorie, statut, CodePostal_ticket, tel_num) VALUES (?, ?, ?, ?, ?, ?, ?)");
        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }

    private CitoyenService() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final CitoyenService.Queries queryEnum = Enum.valueOf(CitoyenService.Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case INSERT_CITOYEN:
                response = InsertCitoyen(request, connection);
                break;
            case INSERT_INCIDENT:
                response = InsertIncident(request, connection);
                break;
            case INSERT_SUGGESTION:
                response = InsertSuggestion(request, connection);
                break;
            default:
                break;
        }

        return response;
    }



}

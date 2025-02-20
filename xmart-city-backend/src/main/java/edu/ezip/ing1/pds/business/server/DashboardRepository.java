package edu.ezip.ing1.pds.business.server;

import edu.ezip.ing1.pds.business.dto.DashboardData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DashboardRepository {

    private enum Queries {
        TOTAL_INCIDENTS("SELECT COUNT(*) FROM incidents"),
        INCIDENTS_EN_COURS("SELECT COUNT(*) FROM incidents WHERE status = 0"),
        INCIDENTS_RESOLU("SELECT COUNT(*) FROM incidents WHERE status = 1"),
        NON_DEFINI("SELECT COUNT(*) FROM incidents WHERE niveau_urgence = 0"),
        FAIBLE("SELECT COUNT(*) FROM incidents WHERE niveau_urgence = 1"),
        MOYEN("SELECT COUNT(*) FROM incidents WHERE niveau_urgence = 2"),
        HAUT("SELECT COUNT(*) FROM incidents WHERE niveau_urgence = 3");

        private final String query;

        Queries(final String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }

    public DashboardData fetchDashboardStats(final Connection connection) throws SQLException {
        final Statement stmt = connection.createStatement();

        int totalIncident = countIncidents(stmt, Queries.TOTAL_INCIDENTS.getQuery());
        int incidentEnCours = countIncidents(stmt, Queries.INCIDENTS_EN_COURS.getQuery());
        int incidentResolu = countIncidents(stmt, Queries.INCIDENTS_RESOLU.getQuery());
        int incidentNonOuvert = totalIncident - incidentEnCours - incidentResolu;

        int nonDefini = countIncidents(stmt, Queries.NON_DEFINI.getQuery());
        int faible = countIncidents(stmt, Queries.FAIBLE.getQuery());
        int moyen = countIncidents(stmt, Queries.MOYEN.getQuery());
        int haut = countIncidents(stmt, Queries.HAUT.getQuery());

        return new DashboardData ();
    }

    private int countIncidents(Statement stmt, String query) throws SQLException {
        ResultSet res = stmt.executeQuery(query);
        if (res.next()) {
            return res.getInt(1);
        }
        return 0;
    }
}


package edu.ezip.ing1.pds.business.server;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.DashboardDto.AdminDashboard;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;

public class CompteAdmin {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        AUTHENTIFICATION("SELECT * FROM Administrateur WHERE Username = ? AND Password = ?"),
        ENREGISTREMENT("INSERT INTO Administrateur (Username, Password) VALUES (?, ?)");

        private final String query;

        Queries(final String query) {
            this.query = query;
        }
    }

    public static CompteAdmin inst = null;

    public static final CompteAdmin getInstance() {
        if (inst == null) {
            inst = new CompteAdmin();
        }
        return inst;
    }

    private CompteAdmin() {}

    public final Response dispatch(final Request request, final Connection connection)
            throws SQLException, IOException {

        final Queries queryEnum = Enum.valueOf(CompteAdmin.Queries.class, request.getRequestOrder());
        final ObjectMapper objectMapper = new ObjectMapper();
        AdminDashboard adminDashboard = objectMapper.readValue(request.getRequestBody(), AdminDashboard.class);
        Response response = null;

        switch (queryEnum) {
            case AUTHENTIFICATION:
                response = authentifier(request, connection, adminDashboard);
                break;
            case ENREGISTREMENT:
                response = enregistrer(request, connection, adminDashboard);
                break;
        }

        return response;
    }

    private Response authentifier(final Request request, final Connection connection, AdminDashboard adminDashboard)
            throws SQLException, IOException {

        try (PreparedStatement ps = connection.prepareStatement(Queries.AUTHENTIFICATION.query)) {
            ps.setString(1, adminDashboard.getUsername());
            ps.setString(2, adminDashboard.getPassword()); 
            try (ResultSet rs = ps.executeQuery()) {
                boolean success = rs.next(); // Si on trouve un utilisateur => login OK
                return new Response(request.getRequestId(), String.valueOf(success));
            }
        }
    }

    private Response enregistrer(final Request request, final Connection connection, AdminDashboard adminDashboard)
            throws SQLException, IOException {

        try (PreparedStatement ps = connection.prepareStatement(Queries.ENREGISTREMENT.query)) {
            ps.setString(1, adminDashboard.getUsername());
            ps.setString(2, adminDashboard.getPassword());

            int rowsInserted = ps.executeUpdate();
            boolean success = rowsInserted > 0;

            return new Response(request.getRequestId(), String.valueOf(success));
        }
    }
}
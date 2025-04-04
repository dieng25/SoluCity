package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Fonctionnaire;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;

public class FonctionnaireService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        AUTHENTICATE("SELECT * FROM Fonctionnaire WHERE email = ? AND mdp = ?"),
        REGISTER("INSERT INTO Fonctionnaire (email, mdp, code_postal) VALUES (?, ?, ?)");

        private final String query;

        Queries(final String query) {
            this.query = query;
        }
    }

    public static FonctionnaireService inst = null;

    public static final FonctionnaireService getInstance() {
        if (inst == null) {
            inst = new FonctionnaireService();
        }
        return inst;
    }

    private FonctionnaireService() {}

    public final Response dispatch(final Request request, final Connection connection)
            throws SQLException, IOException {

        final Queries queryEnum = Enum.valueOf(FonctionnaireService.Queries.class, request.getRequestOrder());
        final ObjectMapper objectMapper = new ObjectMapper();
        Fonctionnaire fonctionnaire = objectMapper.readValue(request.getPayload(), Fonctionnaire.class);
        Response response = null;

        switch (queryEnum) {
            case AUTHENTICATE:
                response = authenticate(request, connection, fonctionnaire);
                break;
            case REGISTER:
                response = register(request, connection, fonctionnaire);
                break;
        }

        return response;
    }

    private Response authenticate(final Request request, final Connection connection, Fonctionnaire fonctionnaire)
            throws SQLException, IOException {

        try (PreparedStatement ps = connection.prepareStatement(Queries.AUTHENTICATE.query)) {
            ps.setString(1, fonctionnaire.getEmail());
            ps.setString(2, fonctionnaire.getMdp());

            try (ResultSet rs = ps.executeQuery()) {
                boolean success = rs.next(); // Si on trouve un utilisateur => login OK
                return new Response(request.getRequestId(), String.valueOf(success));
            }
        }
    }

    private Response register(final Request request, final Connection connection, Fonctionnaire fonctionnaire)
            throws SQLException, IOException {

        try (PreparedStatement ps = connection.prepareStatement(Queries.REGISTER.query)) {
            ps.setString(1, fonctionnaire.getEmail());
            ps.setString(2, fonctionnaire.getMdp());
            ps.setString(3, fonctionnaire.getCodePostal());

            int rowsInserted = ps.executeUpdate();
            boolean success = rowsInserted > 0;

            return new Response(request.getRequestId(), String.valueOf(success));
        }
    }
}

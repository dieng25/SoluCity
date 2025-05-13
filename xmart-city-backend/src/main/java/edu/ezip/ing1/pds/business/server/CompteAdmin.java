package edu.ezip.ing1.pds.business.server;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.DashboardDto.AdminDashboard;
import edu.ezip.ing1.pds.business.dto.DashboardDto.UserDashboard;
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
        ENREGISTREMENT("INSERT INTO Administrateur (Username, Password) VALUES (?, ?)"),
        USER_AUTH("SELECT * FROM Acces WHERE CodePostal = ? AND Utilisateur = ? AND MotDePasse = ?"),
        USER_ENG("INSERT INTO Acces (CodePostal, Utilisateur, MotDePasse) VALUES (?, ?, ?)"),
        ADMIN_UPDATE("UPDATE Administrateur SET Username = ?, Password = ?  WHERE Username = ? AND Password =?"),
        USER_UPDATE("UPDATE Acces SET Utilisateur = ?, MotDePasse = ? WHERE CodePostal = ?");

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
        //AdminDashboard adminDashboard = objectMapper.readValue(request.getRequestBody(), AdminDashboard.class);
        //UserDashboard userDashboard = objectMapper.readValue(request.getRequestBody(), UserDashboard.class);
        Response response = null;

        switch (queryEnum) {
            case AUTHENTIFICATION: {
                AdminDashboard adminDashboard = objectMapper.readValue(request.getRequestBody(), AdminDashboard.class);
                response = authentifier(request, connection, adminDashboard);
                break;
        }
            case ENREGISTREMENT: {
                AdminDashboard adminDashboard = objectMapper.readValue(request.getRequestBody(), AdminDashboard.class);
                response = enregistrer(request, connection, adminDashboard);
                break;
            }
            case USER_AUTH: {
                UserDashboard userDashboard = objectMapper.readValue(request.getRequestBody(), UserDashboard.class);
                response = autoriser(request, connection, userDashboard);
                break;
            }
            case USER_ENG: {
                UserDashboard userDashboard = objectMapper.readValue(request.getRequestBody(), UserDashboard.class);
                response = introduire(request, connection, userDashboard);
                break;
            }
            case ADMIN_UPDATE:{
                AdminDashboard adminDashboard = objectMapper.readValue(request.getRequestBody(), AdminDashboard.class);
                response = MajAdmin(request, connection, adminDashboard);
                break;
            }
            case USER_UPDATE: {
                UserDashboard userDashboard = objectMapper.readValue(request.getRequestBody(), UserDashboard.class);
                response = MajUser(request, connection, userDashboard);
                break;
            }
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
        }catch (SQLIntegrityConstraintViolationException e) {
            // Pour si l'utilisateur met une adresse mail déja existante et autres
            return new Response(request.getRequestId(), "false");
        }
    }

    private Response autoriser(final Request request, final Connection connection, UserDashboard userDashboard)
            throws SQLException, IOException {

        try (PreparedStatement ps = connection.prepareStatement(Queries.USER_AUTH.query)) {
            ps.setString(1, userDashboard.getCodePostal());
            ps.setString(2, userDashboard.getUtilisateur());
            ps.setString(3, userDashboard.getMotDePasse()); 
            try (ResultSet rs = ps.executeQuery()) {
                boolean success = rs.next(); // Si on trouve un utilisateur => login OK
                return new Response(request.getRequestId(), String.valueOf(success));
            }
        }
    }

    private Response introduire(final Request request, final Connection connection, UserDashboard userDashboard)
            throws SQLException, IOException {

        try (PreparedStatement ps = connection.prepareStatement(Queries.USER_ENG.query)) {
            ps.setString(1, userDashboard.getCodePostal());
            ps.setString(2, userDashboard.getUtilisateur());
            ps.setString(3, userDashboard.getMotDePasse());

            int rowsInserted = ps.executeUpdate();
            boolean success = rowsInserted > 0;

            return new Response(request.getRequestId(), String.valueOf(success));
        }
        catch (SQLIntegrityConstraintViolationException e) {
            // Code postal déja utilisé
            return new Response(request.getRequestId(), "false");
        }
    }

    public Response MajAdmin(final Request request, final Connection connection, AdminDashboard adminDashboard)
        throws SQLException, IOException {

    try (PreparedStatement ps = connection.prepareStatement(Queries.ADMIN_UPDATE.query)) {
        ps.setString(1, adminDashboard.getNewUsername());
        ps.setString(2, adminDashboard.getNewPassword());
        ps.setString(3, adminDashboard.getUsername());
        ps.setString(4, adminDashboard.getPassword()); 

        int rowsUpdated = ps.executeUpdate();
        boolean success = rowsUpdated > 0;

        return new Response(request.getRequestId(), String.valueOf(success));
    }
    catch (SQLIntegrityConstraintViolationException e) {
        // Pour si l'utilisateur met une adresse mail déja existante et autres
        return new Response(request.getRequestId(), "false");
    }
}

public Response MajUser(final Request request, final Connection connection, final UserDashboard userDashboard)
        throws SQLException, IOException {

    try (PreparedStatement ps = connection.prepareStatement(Queries.USER_UPDATE.query)) {
        ps.setString(1, userDashboard.getUtilisateur()); 
        ps.setString(2, userDashboard.getMotDePasse());  
        ps.setString(3, userDashboard.getCodePostal());  

        int rowsUpdated = ps.executeUpdate();
        boolean success = rowsUpdated > 0;

        return new Response(request.getRequestId(), String.valueOf(success));
    }catch (SQLIntegrityConstraintViolationException e) {
        return new Response(request.getRequestId(), "false");
    }
}
}
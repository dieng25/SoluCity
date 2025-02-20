package edu.ezip.ing1.pds.business.server;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Citoyen;
import edu.ezip.ing1.pds.business.dto.Incident;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Random;

public class CitoyenService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_CITOYEN("INSERT INTO Citoyen (tel_num, Nom, Prénom, email, Identifiant) VALUES (?, ?, ?, ?, ?)"),
        INSERT_INCIDENT("INSERT INTO Incident (Titre, Description, date_emis, Catégorie, Statut, CodePostal_ticket, Priorité) VALUES (?, ?, ?, ?, ?, ?, ?)");
        //SELECT_INCIDENT("SELECT * FROM Incident WHERE Id_ticket = ? AND tel_num = ?"),
        //UPDATE_INCIDENT("UPDATE Incident SET Titre = ?, Description = ? WHERE Id_ticket = ? AND Statut = 0");
        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

    }

    public static CitoyenService inst = null;

    public static final CitoyenService getInstance() {
        if (inst == null) {
            inst = new CitoyenService();
        }
        return inst;
    }

    private CitoyenService() {
    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(CitoyenService.Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case INSERT_CITOYEN:
                response = InsertCitoyen(request, connection);
                break;
            case INSERT_INCIDENT:
                response = InsertIncident(request, connection);
                break;
            //case SELECT_INCIDENT:
               // response = selectIncident(request, connection);
               // break;
            //case UPDATE_INCIDENT:
              //  response = updateIncident(request, connection);
              //  break;
           // default:
             //   break;
       }

       return response;
    }

    private Response InsertCitoyen(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        Citoyen citoyen = objectMapper.readValue(request.getRequestBody(), Citoyen.class);

        //Crée un Identifiant Unique -> Champ Identifiant table Citoyen
        String identifiant = generateIdentifiant(citoyen.getPrenom(), citoyen.getNom());
        citoyen.setIdentifiant(identifiant);

        PreparedStatement stmt = connection.prepareStatement(CitoyenService.Queries.INSERT_CITOYEN.query);
        stmt.setString(1, citoyen.getTelNum());
        stmt.setString(2, citoyen.getNom());
        stmt.setString(3, citoyen.getPrenom());
        stmt.setString(4, citoyen.getEmail());
        stmt.setString(5, citoyen.getIdentifiant());
        int rowsInserted = stmt.executeUpdate();
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(citoyen));
    }

    private String generateIdentifiant(String prenom, String nom) {
        String firstLetterPrenom = prenom.substring(0, 1).toUpperCase();
        String firstLetterNom = nom.substring(0, 1).toUpperCase();
        String lastLetterNom = nom.substring(nom.length() - 1).toUpperCase();
        String randomPart = generateRandomString(4);
        return firstLetterPrenom + firstLetterNom + lastLetterNom + randomPart;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }


    private Response InsertIncident(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_INCIDENT.query, Statement.RETURN_GENERATED_KEYS);
        Incident incident = objectMapper.readValue(request.getRequestBody(), Incident.class);
        stmt.setString(1, incident.getTitre());
        stmt.setString(2, incident.getDescription());
        stmt.setString(3, incident.getDate());
        stmt.setString(4, incident.getCategorie());
        stmt.setInt(5, incident.getStatut());
        stmt.setInt(6, incident.getCP_Ticket());
        stmt.setInt(7, incident.getPriorite());
        int rowsInserted = stmt.executeUpdate();


        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            incident.setIdTicket(generatedKeys.getInt(1));
        }

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(incident));
    }




}





package edu.ezip.ing1.pds.services.Dashboard;   
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.business.dto.DashboardDto.AdminDashboard;
import edu.ezip.ing1.pds.requests.DashboardRequest.EnregistrementAdmin;
import edu.ezip.ing1.pds.requests.DashboardRequest.AuthentificationAdmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

public class AdminDashboardService {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardService.class);
    private final NetworkConfig networkConfig;

    public AdminDashboardService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public boolean EnregistrementAdmin(AdminDashboard adminDashboard) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder("ENREGISTREMENT");
    
        try {
            String adminJson = objectMapper.writeValueAsString(adminDashboard);
            request.setRequestContent(adminJson); 
    
            final byte[] requestBytes = objectMapper.writeValueAsBytes(request);
    
            EnregistrementAdmin clientRequest = new EnregistrementAdmin(
                    networkConfig,
                    0,
                    request,
                    adminDashboard,
                    requestBytes
            );
    
          
            clientRequest.join();
    
            return (boolean) clientRequest.getResult();
        } catch (InterruptedException e) {
            logger.error("Thread interrompu lors de l'inscription.", e);
            return false;
        } catch (IOException e) {
            logger.error("Erreur de sérialisation lors de la requête d'inscription.", e);
            return false;
        }
    }
    

    // Authentification d'un fonctionnaire
    public boolean AuthentificationAdmin(AdminDashboard adminDashboard) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder("AUTHENTIFICATION");
        

        try {
            // Sérialiser la requête en JSON
            final byte[] requestBytes = objectMapper.writeValueAsBytes(request);
            String adminJson = objectMapper.writeValueAsString(adminDashboard);
            request.setRequestContent(adminJson);
            AuthentificationAdmin clientRequest = new AuthentificationAdmin(
                    networkConfig,
                    0,
                    request,
                    adminDashboard,
                    requestBytes
            );

            // Attendre la réponse de la requête
            clientRequest.join();
            return (boolean) clientRequest.getResult();
        } catch (InterruptedException e) {
            // Capturer l'exception d'interruption
            logger.error("Thread interomppu lors de l'inscription.", e);
            return false;

        } catch (IOException e) {
            // Capturer les erreurs d'entrée/sortie (erreurs de sérialisation)
            logger.error("Error de serialisation lors de la requete pour l'authentication.", e);
            return false;
        }
    }
}

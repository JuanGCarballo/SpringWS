package es.neesis.soapclient.client;

import es.neesis.soapclient.ws.user.GetUserRequest;
import es.neesis.soapclient.ws.user.GetUserResponse;
import es.neesis.soapclient.ws.user.LoginRequest;
import es.neesis.soapclient.ws.user.LoginResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.Base64;

public class UserClient extends WebServiceGatewaySupport {

    public GetUserResponse getUser(int id) {
        GetUserRequest request = new GetUserRequest();
        request.setId(id);

        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public boolean login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        String hashPassword = Base64.getEncoder().encodeToString(password.getBytes());
        request.setPassword(hashPassword);

        LoginResponse response = (LoginResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        if (response.isAuthenticated()) {
            System.out.println("Login correcto para el usuario: " + username);
            return true;
        } else {
            System.out.println("Credenciales incorrectas para el usuario: " + username);
            return false;
        }
    }
}

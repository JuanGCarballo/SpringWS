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

    public LoginResponse login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        String hashPassword = Base64.getEncoder().encodeToString(password.getBytes());
        request.setPassword(hashPassword);

        return (LoginResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}

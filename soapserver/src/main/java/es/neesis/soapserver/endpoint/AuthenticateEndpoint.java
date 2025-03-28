package es.neesis.soapserver.endpoint;

import es.neesis.soapserver.servicios.AuthService;
import es.neesis.soapserver.ws.user.GetAuthRequest;
import es.neesis.soapserver.ws.user.GetAuthResponse;
import es.neesis.soapserver.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

public class AuthenticateEndpoint {

    private static final String NAMESPACE_URI = "http://www.neesis.es/soapserver/ws/user";

    private final AuthService authService;

    @Autowired
    public AuthenticateEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAuthRequest")
    @ResponsePayload
    public GetAuthResponse getUser(@RequestPayload GetAuthRequest request) {
        GetAuthResponse response = new GetAuthResponse();

        try {
            User user = this.authService.authenticate(request.getUsername(), request.getPassword());
            response.setUser(user);
            response.setCodigo("200");
            response.setMensaje("OK");
        } catch (Exception e) {
            response.setCodigo("404");
            response.setMensaje("KO");
        }

        return response;
    }

}

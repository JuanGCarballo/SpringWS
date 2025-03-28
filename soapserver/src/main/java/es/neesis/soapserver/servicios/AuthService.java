package es.neesis.soapserver.servicios;

import es.neesis.soapserver.ws.user.User;

public interface AuthService {
    User authenticate(String username, String password);
}

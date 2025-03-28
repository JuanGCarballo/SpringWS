package es.neesis.soapserver.servicios;

import es.neesis.soapserver.repository.FakeUserDB;
import es.neesis.soapserver.ws.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {

    private FakeUserDB fakeUserDB;

    public AuthServiceImpl(FakeUserDB fakeUserDB) {
        this.fakeUserDB = fakeUserDB;
    }

    @Override
    public User authenticate(String username, String password) {
        User user = this.getUser(username, this.decodePassword(password));

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        } else if (this.passwordIsExpired(user)) {
            throw new RuntimeException("Contraseña expirada");
        }
        
        this.actualizarFechaUltimoLogin(user);
        return user;
    }

    private String decodePassword(String password) {
        return new String(Base64.getDecoder().decode(password));
    }

    private void actualizarFechaUltimoLogin(User user) {
        user.setUltimoLogin(LocalDateTime.now().toLocalTime());
    }

    private boolean passwordIsExpired(User user) {
        return LocalDateTime.now().isAfter(LocalDateTime.parse(user.getFechaExpiracion()));
    }

}

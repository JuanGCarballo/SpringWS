package es.neesis.soapserver.servicios;

import es.neesis.soapserver.repository.FakeUserDB;
import es.neesis.soapserver.ws.user.User;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
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
        User user = this.fakeUserDB.getUser(username, this.decodePassword(password));

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
        user.setUltimoLogin(XMLGregorianCalendar.class.cast(LocalDateTime.now()));
    }

    private boolean passwordIsExpired(User user) {
        XMLGregorianCalendar fechaExpiracion = user.getFechaExpiracion();
        XMLGregorianCalendar fechaActual = XMLGregorianCalendar.class.cast(LocalDateTime.now());
        return fechaActual.compare(fechaExpiracion) == 1;
    }

}

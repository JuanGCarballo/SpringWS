package es.neesis.soapserver.repository;

import es.neesis.soapserver.ws.user.Address;
import es.neesis.soapserver.ws.user.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class FakeUserDB {

    private static final Map<Integer, User> dbUsers = new HashMap<>();

    @PostConstruct
    public void initData() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setEmail("user@mail.com");
        Address address1 = new Address();
        address1.setDireccion("Calle 1");
        address1.setCiudad("Ciudad 1");
        address1.setPais("Pais 1");
        user1.setAddress(address1);
        user1.setFechaExpiracion(XMLGregorianCalendar.class.cast(LocalDateTime.now().plusDays(10)));
        user1.setUltimoLogin(XMLGregorianCalendar.class.cast(LocalDateTime.now()));

        dbUsers.put(user1.getId(), user1);
    }

    public User getUser(int id) {
        return dbUsers.get(id);
    }

    public User getUser(String username, String password) {
        return dbUsers.values().stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password)).findFirst().orElse(null);
    }
}

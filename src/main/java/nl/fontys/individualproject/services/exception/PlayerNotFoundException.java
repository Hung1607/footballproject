package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class PlayerNotFoundException extends ResponseStatusException {
    public PlayerNotFoundException(HttpStatusCode status) {
        super(status);
    }
}

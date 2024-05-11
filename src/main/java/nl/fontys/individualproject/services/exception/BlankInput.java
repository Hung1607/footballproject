package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BlankInput extends ResponseStatusException {
    public BlankInput(){
        super(HttpStatus.BAD_REQUEST,"Field can not be blank");
    }
}

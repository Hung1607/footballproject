package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IdNotfoundException extends ResponseStatusException {
    public IdNotfoundException(){
        super(HttpStatus.BAD_REQUEST,"ID does not exist");
    }
}

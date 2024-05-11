package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyExist extends ResponseStatusException {
    public UsernameAlreadyExist(){
        super(HttpStatus.BAD_REQUEST,"Username already existed");
    }
}

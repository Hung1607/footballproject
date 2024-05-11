package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameNotFound extends ResponseStatusException {
    public UsernameNotFound(){
        super(HttpStatus.BAD_REQUEST,"UserName not existed");
    }
}

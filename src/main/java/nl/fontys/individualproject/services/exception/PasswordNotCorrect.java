package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordNotCorrect extends ResponseStatusException {
    public PasswordNotCorrect(){
        super(HttpStatus.BAD_REQUEST,"Password does not match");
    }
}

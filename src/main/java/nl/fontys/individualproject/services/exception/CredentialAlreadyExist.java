package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CredentialAlreadyExist extends ResponseStatusException {
    public CredentialAlreadyExist(){
        super(HttpStatus.BAD_REQUEST,"Gmail already existed");
    }
}

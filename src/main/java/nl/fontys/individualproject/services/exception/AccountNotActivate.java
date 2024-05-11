package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountNotActivate extends ResponseStatusException {
    public AccountNotActivate(){
        super(HttpStatus.BAD_REQUEST,"Account is not Activated");
    }
}

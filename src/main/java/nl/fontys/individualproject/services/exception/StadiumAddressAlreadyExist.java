package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StadiumAddressAlreadyExist extends ResponseStatusException {
    public StadiumAddressAlreadyExist(){
        super(HttpStatus.BAD_REQUEST,"Stadium Address already exist ! ");
    }

}

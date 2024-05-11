package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DateTimeInvalid extends ResponseStatusException {
    public DateTimeInvalid(){
        super(HttpStatus.BAD_REQUEST,"DateTime is Invalid");
    }
}

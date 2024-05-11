package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MatchIDNotFound extends ResponseStatusException {
    public MatchIDNotFound(){
        super(HttpStatus.BAD_REQUEST,"Match ID Not Found");
    }
}

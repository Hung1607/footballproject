package nl.fontys.individualproject.services.exception.match;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;

public class OutTimeIsOver extends ResponseStatusException {
    public OutTimeIsOver(){
        super(HttpStatus.BAD_REQUEST,"OutTime is Over");
    }
}

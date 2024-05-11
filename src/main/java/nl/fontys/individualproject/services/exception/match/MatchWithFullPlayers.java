package nl.fontys.individualproject.services.exception.match;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MatchWithFullPlayers extends ResponseStatusException {
    public MatchWithFullPlayers(){
        super(HttpStatus.BAD_REQUEST,"Account is not Activated");
    }
}

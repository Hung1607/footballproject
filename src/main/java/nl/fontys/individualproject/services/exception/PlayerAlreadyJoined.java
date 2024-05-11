package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerAlreadyJoined extends ResponseStatusException {
    public PlayerAlreadyJoined(){
        super(HttpStatus.BAD_REQUEST,"Player already joined");
    }
}

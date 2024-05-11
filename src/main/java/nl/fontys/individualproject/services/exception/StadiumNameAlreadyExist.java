package nl.fontys.individualproject.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StadiumNameAlreadyExist extends ResponseStatusException {

        public StadiumNameAlreadyExist(){
            super(HttpStatus.BAD_REQUEST,"Stadium Name already exist");
        }
}

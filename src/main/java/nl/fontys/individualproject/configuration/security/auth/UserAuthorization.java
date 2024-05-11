package nl.fontys.individualproject.configuration.security.auth;

import lombok.AllArgsConstructor;
import nl.fontys.individualproject.configuration.security.token.AccessToken;
import nl.fontys.individualproject.services.exception.UnauthorizedDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthorization {
    private AccessToken requestAccessToken;
    public void authorize(int userId){
        if(requestAccessToken.getUserId() != userId){
            throw new UnauthorizedDataAccessException("User_ID_NOT_FROM_LOGGED_IN_USER");
        }
    }
}

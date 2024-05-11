package nl.fontys.individualproject.services.validation.login;

import lombok.AllArgsConstructor;
import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.exception.UsernameNotFound;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginValidation {
    private UserRepository userRepository;
    public void checkingUsername(String username){
        if(!this.userRepository.existsByUsername(username)){
            throw new UsernameNotFound();
        }
    }
}

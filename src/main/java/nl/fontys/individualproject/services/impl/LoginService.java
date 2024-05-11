package nl.fontys.individualproject.services.impl;

import lombok.AllArgsConstructor;
import nl.fontys.individualproject.configuration.security.token.AccessTokenEncoder;
import nl.fontys.individualproject.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.individualproject.domain.response.Admin;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.LoginResponse;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.repositories.Entity.UserEntity;
import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.exception.AccountNotActivate;
import nl.fontys.individualproject.services.exception.PasswordNotCorrect;
import nl.fontys.individualproject.services.validation.login.LoginValidation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoginService {
    private UserRepository userRepository;
    private LoginValidation loginValidation;

    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    public LoginResponse getLogin(String username, String password){
        this.loginValidation.checkingUsername(username);

        UserEntity userEntity =  this.userRepository.getByUsername(username);

        // Checking Coach Status
        if(userEntity instanceof CoachEntity coachEntity){
            if(Objects.equals(coachEntity.getStatus(), "PENDING") || Objects.equals(coachEntity.getStatus(), "DECLINE")){
                throw new AccountNotActivate();
            }
        }

        if(matchesPassword(password,userEntity.getPassword())){
            String accessToken = generateAccessToken(userEntity);
            return LoginResponse.builder().accessToken(accessToken).build();
        }
        throw new PasswordNotCorrect();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity userEntity) {
        int userId = userEntity.getId();

        List<String> roles = new ArrayList<>();
        if(userEntity instanceof PlayerEntity){
            roles.add(Player.class.getSimpleName());
        }
        else if(userEntity instanceof CoachEntity) {
            roles.add(Coach.class.getSimpleName());
        }
        else{
            roles.add(Admin.class.getSimpleName());
        }

        return accessTokenEncoder.encode(
                new AccessTokenImpl(userEntity.getUsername(), userId, roles));
    }
}

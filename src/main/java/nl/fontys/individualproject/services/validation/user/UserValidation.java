package nl.fontys.individualproject.services.validation.user;

import lombok.AllArgsConstructor;
import nl.fontys.individualproject.domain.response.User;
import nl.fontys.individualproject.repositories.Entity.UserEntity;
import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.exception.BlankInput;
import nl.fontys.individualproject.services.exception.GmailAlreadyExist;
import nl.fontys.individualproject.services.exception.IdNotfoundException;
import nl.fontys.individualproject.services.exception.UsernameAlreadyExist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserValidation {

    private final UserRepository uR;

    public void checkingValidGmail(String gmail){
        if(gmail.isBlank()){
            throw new BlankInput();
        }
    }

    public void checkingValidUsername(String username){
        if(username.isBlank()){
            throw new BlankInput();
        }
    }
    public void checkingUserExistWithID(int id){
        if(!this.uR.existsById(id)){
            throw new IdNotfoundException();
        }
    }

    public void checkingUserUsernameExist(String username){
        if(this.uR.existsByUsername(username)){
            throw new UsernameAlreadyExist();
        }
    }

    public void checkingUserGmailExist(String gmail){
        if(this.uR.existsByGmail(gmail)){
            throw new GmailAlreadyExist();
        }
    }

    public void checkingUserExist(Optional<UserEntity> userEntity){
        if(userEntity.isEmpty()){
            throw new IdNotfoundException();
        }
    }

    public void checkingDuplicateExist(List<UserEntity> userDuplicateList,User user){
        if(!userDuplicateList.isEmpty()){
            for(UserEntity userDuplicate : userDuplicateList){
                if(Objects.equals(userDuplicate.getUsername(), user.getUsername())){
                    throw new UsernameAlreadyExist();
                }
                else if (Objects.equals(userDuplicate.getGmail(), user.getGmail())){
                    throw new GmailAlreadyExist();
                }
            }
        }
    }
}

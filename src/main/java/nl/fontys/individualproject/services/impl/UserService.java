package nl.fontys.individualproject.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.individualproject.configuration.security.auth.UserAuthorization;
import nl.fontys.individualproject.domain.request.AdminRequest;
import nl.fontys.individualproject.domain.request.CoachRequest;
import nl.fontys.individualproject.domain.request.PlayerRequest;
import nl.fontys.individualproject.domain.response.Admin;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.domain.response.User;
import nl.fontys.individualproject.repositories.AdminRepository;
import nl.fontys.individualproject.repositories.CoachRepository;
import nl.fontys.individualproject.repositories.Entity.AdminEntity;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.repositories.Entity.UserEntity;
import nl.fontys.individualproject.repositories.PlayerRepository;
import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.CoachCreate;
import nl.fontys.individualproject.services.PlayerCreate;
import nl.fontys.individualproject.services.UserFunctionality;
import nl.fontys.individualproject.services.impl.converter.AdminConvert;
import nl.fontys.individualproject.services.impl.converter.CoachConvert;
import nl.fontys.individualproject.services.impl.converter.PlayerConvert;
import nl.fontys.individualproject.services.validation.user.UserValidation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements PlayerCreate, CoachCreate, UserFunctionality {

    private final UserRepository uR;
    private UserValidation userValidation;

    private final PlayerRepository playerRepository;
    private final CoachRepository coachRepository;
    private AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private UserAuthorization userAuthorization; // checking logged user

    public List<User> getAllUser(Class<?> classType){
        List<UserEntity> availableUser;
        List<PlayerEntity> playerEntities = null;
        List<CoachEntity> coachEntities = null;

        if(classType.equals(Player.class)){
            playerEntities = new ArrayList<>();
            classType = PlayerEntity.class;
            availableUser = this.uR.findAll();
            for (UserEntity userEntity : availableUser) {
                if(userEntity instanceof PlayerEntity playerEntity){
                    playerEntities.add(playerEntity);
                }
            }
        }
        else if(classType.equals(Coach.class)){
            coachEntities = new ArrayList<>();
            classType = CoachEntity.class;
            availableUser = this.uR.findAll();
            for (UserEntity userEntity : availableUser) {
                if(userEntity instanceof CoachEntity coachEntity){
                    coachEntities.add(coachEntity);
                }
            }
        }

        if(classType.equals(PlayerEntity.class) && playerEntities != null){
            List<Player> players =  playerEntities
                    .stream()
                    .map(PlayerConvert::convert)
                    .toList();
            return players.stream().map(p -> (User)p).toList();
        }
        else if (classType.equals(CoachEntity.class) && coachEntities != null) {
            List<Coach> coaches = coachEntities
                    .stream()
                    .map(CoachConvert::convert)
                    .toList();
            return coaches.stream().map(p -> (User)p).toList();
        }
        return Collections.emptyList();
    }

    public Optional<User> getUserByID(int id,Class<?> classType){

        this.userAuthorization.authorize(id);

        Optional<UserEntity> userEntity = this.uR.findById(id);

        this.userValidation.checkingUserExist(userEntity);

        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();
            if (entity instanceof PlayerEntity playerEntity && classType.equals(Player.class)) {
                return Optional.of(playerEntity).map(PlayerConvert::convert);
            }
            if (entity instanceof CoachEntity coachEntity && classType.equals(Coach.class)) {
                return Optional.of(coachEntity).map(CoachConvert::convert);
            }
            if (entity instanceof AdminEntity adminEntity && classType.equals(Admin.class)) {
                return Optional.of(adminEntity).map(AdminConvert::convert);
            }
        }
        return Optional.empty();
    }

    public void deleteUserById(int id) {
        this.userAuthorization.authorize(id);
        this.userValidation.checkingUserExistWithID(id);
        this.uR.deleteById(id);
    }

    public void createPlayer(PlayerRequest playerRequest){
        this.userValidation.checkingValidGmail(playerRequest.getGmail());
        this.userValidation.checkingValidUsername(playerRequest.getUsername());

        this.userValidation.checkingUserGmailExist(playerRequest.getGmail());
        this.userValidation.checkingUserUsernameExist(playerRequest.getUsername());

        String encodedPassword = passwordEncoder.encode(playerRequest.getPassword());

        this.playerRepository.save(PlayerEntity.builder()
                            .gmail(playerRequest.getGmail())
                            .username(playerRequest.getUsername())
                            .password(encodedPassword)
                            .playingLocation(playerRequest.getPlayingLocation())
                            .dateOfBirth(playerRequest.getDateOfBirth())
                            .position(playerRequest.getPosition())
                            .build());
}

    public void createCoach(CoachRequest coachRequest){
        this.userValidation.checkingValidGmail(coachRequest.getGmail());
        this.userValidation.checkingValidUsername(coachRequest.getUsername());

        this.userValidation.checkingUserGmailExist(coachRequest.getGmail());
        this.userValidation.checkingUserUsernameExist(coachRequest.getUsername());

        String encodedPassword = passwordEncoder.encode(coachRequest.getPassword());

        this.coachRepository.save(CoachEntity.builder()
                                        .gmail(coachRequest.getGmail())
                                        .username(coachRequest.getUsername())
                                        .password(encodedPassword)
                                        .dateOfBirth(coachRequest.getDateOfBirth())
                                        .playingLocation(coachRequest.getPlayingLocation())
                                        .experience(coachRequest.getExperience())
                                        .description(coachRequest.getDescription())
                                        .status("PENDING")
                                        .build());
        }

    public void createAdmin(AdminRequest adminRequest){
        this.userValidation.checkingValidUsername(adminRequest.getUsername());
        this.userValidation.checkingValidGmail(adminRequest.getGmail());

        this.userValidation.checkingUserUsernameExist(adminRequest.getUsername());
        this.userValidation.checkingUserGmailExist(adminRequest.getGmail());

        String encodePassword = passwordEncoder.encode(adminRequest.getPassword());

        this.adminRepository.save(AdminEntity.builder()
                .username(adminRequest.getUsername())
                .password(encodePassword)
                .playingLocation(adminRequest.getWorkingLocation())
                .gmail((adminRequest.getGmail()))
                .dateOfBirth(adminRequest.getDateOfBirth())
                .build());
    }

    @Transactional
    public void updateUser(User user){
        this.userAuthorization.authorize(user.getId());

        this.userValidation.checkingValidGmail(user.getGmail());
        this.userValidation.checkingValidUsername(user.getUsername());

        Optional<UserEntity> userEntity = this.uR.findById(user.getId());
        this.userValidation.checkingUserExist(userEntity);

        List<UserEntity> userDuplicateList = this.uR.getByUsernameAndGmailIgnoreIdCase(user.getUsername(),user.getGmail(),user.getId());
        this.userValidation.checkingDuplicateExist(userDuplicateList,user);

        if(userEntity.isPresent()){
            if(user instanceof Player player && userEntity.get() instanceof PlayerEntity){
                this.playerRepository.updatePlayer(player.getId(),
                        player.getGmail(),
                        player.getUsername(),
                        player.getDateOfBirth(),
                        player.getPlayingLocation(),
                        player.getPosition());
            }
            else if(user instanceof Coach coach && userEntity.get() instanceof CoachEntity){
                this.coachRepository.updateCoach(coach.getId(),
                        coach.getGmail(),
                        coach.getUsername(),
                        coach.getDateOfBirth(),
                        coach.getPlayingLocation(),
                        coach.getDescription(),
                        coach.getExperience());
            }
            else if(user instanceof Admin admin && userEntity.get() instanceof AdminEntity){
                this.adminRepository.updateAdmin(admin.getId(),
                        admin.getGmail(),
                        admin.getUsername(),
                        admin.getDateOfBirth(),
                        admin.getPlayingLocation());
            }
        }
    }



}

package nl.fontys.individualproject.services.validation.user;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.domain.response.User;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.UserEntity;
import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.exception.BlankInput;
import nl.fontys.individualproject.services.exception.GmailAlreadyExist;
import nl.fontys.individualproject.services.exception.IdNotfoundException;
import nl.fontys.individualproject.services.exception.UsernameAlreadyExist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidationTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidation userValidation;

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void checkingValidGmail() {
        this.userValidation.checkingValidGmail("asdas");
    }

    @Test
    void checkingValidGmail_ShouldThrowBlankInput() {
        assertThrows(BlankInput.class, () -> this.userValidation.checkingValidGmail(""));
    }

    @Test
    void checkingValidUsername() {
        this.userValidation.checkingValidUsername("asdas");
    }

    @Test
    void checkingValidUsername_ShouldThrowBlankInput() {
        assertThrows(BlankInput.class, () -> this.userValidation.checkingValidUsername(""));
    }

    @Test
    void checkingUserExistWithID() {
        when(this.userRepository.existsById(Mockito.anyInt())).thenReturn(true);
        this.userValidation.checkingUserExistWithID(12);
    }
    @Test
    void checkingUserExistWithID_ShouldThrowIdNotFoundException() {
        when(this.userRepository.existsById(Mockito.anyInt())).thenReturn(false);
        assertThrows(IdNotfoundException.class, () -> this.userValidation.checkingUserExistWithID(12));
    }


    @Test
    void checkingUserUsernameExist() {
        when(this.userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        this.userValidation.checkingUserUsernameExist("Kai");
    }

    @Test
    void checkingUserUsernameExist_ShouldThrowUsernameAlreadyExist() {
        when(this.userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        assertThrows(UsernameAlreadyExist.class, () -> this.userValidation.checkingUserUsernameExist("Kai"));
    }

    @Test
    void checkingUserGmailExist() {
        when(this.userRepository.existsByGmail(Mockito.anyString())).thenReturn(false);
        this.userValidation.checkingUserGmailExist("Kai");
    }

    @Test
    void checkingUserGmailExist_ShouldThrowGmailAlreadyExist() {
        when(this.userRepository.existsByGmail(Mockito.anyString())).thenReturn(true);
        assertThrows(GmailAlreadyExist.class, () -> this.userValidation.checkingUserGmailExist("Kai"));
    }
    @Test
    void checkingUserExist() {
        this.userValidation.checkingUserExist(Optional.of(new UserEntity()));
    }

    @Test
    void checkingUserExist_ShouldThrowIdNotfoundException() {
        assertThrows(IdNotfoundException.class, () -> this.userValidation.checkingUserExist(Optional.empty()));
    }

    @Test
    void checkingDuplicateExist_EmptyUserDuplicateList() {
        List<UserEntity> empty = new ArrayList<>();
        this.userValidation.checkingDuplicateExist(empty,new User());
    }

    @Test
    void checkingDuplicateExist_ShouldThrowUserNameAlreadyExist() {
        Player update = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        CoachEntity expect_2 = CoachEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        List<UserEntity> userEntityList = new ArrayList<>();

        userEntityList.add(expect_2);

        assertThrows(UsernameAlreadyExist.class,() -> this.userValidation.checkingDuplicateExist(userEntityList,update));
    }
    @Test
    void checkingDuplicateExist_ShouldThrowGmailAlreadyExist() {
        Player update = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        CoachEntity expect_2 = CoachEntity.builder()
                .id(12)
                .username("Jake")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        List<UserEntity> userEntityList = new ArrayList<>();

        userEntityList.add(expect_2);

        assertThrows(GmailAlreadyExist.class,() -> this.userValidation.checkingDuplicateExist(userEntityList,update));
    }

    @Test
    void checkingDuplicateExist_ListWithNoDuplication() {
        Player update = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie12@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        CoachEntity expect_2 = CoachEntity.builder()
                .id(12)
                .username("Jake")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        List<UserEntity> userEntityList = new ArrayList<>();

        userEntityList.add(expect_2);

        this.userValidation.checkingDuplicateExist(userEntityList,update);
    }
}
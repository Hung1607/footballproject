package nl.fontys.individualproject.services.impl;

import nl.fontys.individualproject.configuration.security.token.AccessTokenEncoder;
import nl.fontys.individualproject.configuration.security.token.impl.AccessTokenImpl;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;
import nl.fontys.individualproject.domain.request.PlayerRequest;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.LoginResponse;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.MatchEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.exception.AccountNotActivate;
import nl.fontys.individualproject.services.exception.PasswordNotCorrect;
import nl.fontys.individualproject.services.exception.UsernameNotFound;
import nl.fontys.individualproject.services.validation.login.LoginValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoginValidation loginValidation;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void getLogin_withPlayer() {
        PlayerEntity expected = PlayerEntity.builder()
                .username("jack12")
                .gmail("jackie@gmail.com")
                .password("$2a$10$DU5IdUpSYykD99XlPeF1yOWCRm6DKsILZkMtKtQPSVeN7S0qbhYBK")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.loginValidation).checkingUsername(Mockito.anyString());
        when(this.passwordEncoder.matches(Mockito.any(),Mockito.anyString())).thenReturn(true);
        when(this.userRepository.getByUsername(Mockito.anyString())).thenReturn(expected);
        when(this.accessTokenEncoder.encode(new AccessTokenImpl(expected.getUsername(),
                expected.getId(),
                List.of(Player.class.getSimpleName())))).
                thenReturn("$2a$10$DU5IdUpSYykD99XlPeF1yOWCRm6DKsILZkMtKtQPSVeN7S0qbhYBK");

        LoginResponse loginResponse = this.loginService.getLogin("jack12","123");

        // TODO : assert verify for validation
        verify(loginValidation,times(1)).checkingUsername(anyString());
        verify(userRepository,times(1)).getByUsername(anyString());

        assertNotNull(loginResponse.getAccessToken());
    }

    @Test
    void getLogin_withCoach() {
        CoachEntity expected = CoachEntity.builder()
                .username("jack12")
                .gmail("jackie@gmail.com")
                .password("$2a$10$DU5IdUpSYykD99XlPeF1yOWCRm6DKsILZkMtKtQPSVeN7S0qbhYBK")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("")
                .experience("")
                .build();

        doNothing().when(this.loginValidation).checkingUsername(Mockito.anyString());
        when(this.passwordEncoder.matches(Mockito.any(),Mockito.anyString())).thenReturn(true);
        when(this.userRepository.getByUsername(Mockito.anyString())).thenReturn(expected);
        when(this.accessTokenEncoder.encode(new AccessTokenImpl(expected.getUsername(),
                expected.getId(),
                List.of(Coach.class.getSimpleName())))).
                thenReturn("$2a$10$DU5IdUpSYykD99XlPeF1yOWCRm6DKsILZkMtKtQPSVeN7S0qbhYBK");

        LoginResponse loginResponse = this.loginService.getLogin("jack12","123");

        verify(loginValidation,times(1)).checkingUsername(anyString());
        verify(userRepository,times(1)).getByUsername(anyString());

        assertNotNull(loginResponse.getAccessToken());
    }



    @Test
    void getLogin_ShouldThrowUserNotFoundException(){
        doThrow(UsernameNotFound.class).when(this.loginValidation).checkingUsername(anyString());
        // lambda expression is the implementation of executable
        assertThrows(UsernameNotFound.class, () -> this.loginService.getLogin("Jack","jk1212"));
    }

    @Test
    void getLogin_ShouldThrowPasswordNotCorrect(){
        PlayerEntity expected = PlayerEntity.builder()
                .username("Jack")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.loginValidation).checkingUsername("Jack");

        when(this.userRepository.getByUsername("Jack")).thenReturn(expected);

        assertThrows(PasswordNotCorrect.class, () -> this.loginService.getLogin("Jack","jk1212"));
    }

    @Test
    void getLogin_CheckingCoachStatus_Pending(){
        CoachEntity expected = CoachEntity.builder()
                .username("jack12")
                .gmail("jackie@gmail.com")
                .password("$2a$10$DU5IdUpSYykD99XlPeF1yOWCRm6DKsILZkMtKtQPSVeN7S0qbhYBK")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .status("PENDING")
                .description("")
                .experience("")
                .build();
        doNothing().when(this.loginValidation).checkingUsername(any());

        when(this.userRepository.getByUsername(anyString())).thenReturn(expected);

        assertThrows(AccountNotActivate.class, () -> this.loginService.getLogin("Jack","jk1212"));
    }

    @Test
    void getLogin_CheckingCoachStatus_Decline(){
        CoachEntity expected = CoachEntity.builder()
                .username("jack12")
                .gmail("jackie@gmail.com")
                .password("$2a$10$DU5IdUpSYykD99XlPeF1yOWCRm6DKsILZkMtKtQPSVeN7S0qbhYBK")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .status("DECLINE")
                .description("")
                .experience("")
                .build();
        doNothing().when(this.loginValidation).checkingUsername(any());

        when(this.userRepository.getByUsername(anyString())).thenReturn(expected);

        assertThrows(AccountNotActivate.class, () -> this.loginService.getLogin("Jack","jk1212"));
    }



}
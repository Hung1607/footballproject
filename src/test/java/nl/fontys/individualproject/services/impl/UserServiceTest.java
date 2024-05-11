package nl.fontys.individualproject.services.impl;

import nl.fontys.individualproject.configuration.security.auth.UserAuthorization;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;
import nl.fontys.individualproject.domain.request.AdminRequest;
import nl.fontys.individualproject.domain.request.CoachRequest;
import nl.fontys.individualproject.domain.request.PlayerRequest;
import nl.fontys.individualproject.domain.response.*;
import nl.fontys.individualproject.repositories.AdminRepository;
import nl.fontys.individualproject.repositories.CoachRepository;
import nl.fontys.individualproject.repositories.Entity.*;
import nl.fontys.individualproject.repositories.PlayerRepository;
import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.exception.*;
import nl.fontys.individualproject.services.validation.user.UserValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidation userValidation;
    @Mock
    private CoachRepository coachRepository;
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserAuthorization userAuthorization;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUser_Player() {
        List<UserEntity> expected = new ArrayList<>();
        PlayerEntity player = PlayerEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        CoachEntity coach = CoachEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombok")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can speak dutch and english")
                .experience("2 years with Manchester City")
                .build();
        expected.add(player);
        expected.add(coach);

        when(this.userRepository.findAll()).thenReturn(expected);
        List<User> actual = this.userService.getAllUser(Player.class);

        verify(userRepository, times(1)).findAll();
        assertEquals(1,actual.size());
    }

    @Test
    void getAllUser_Coach() {
        List<UserEntity> expected = new ArrayList<>();
        CoachEntity coach = CoachEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombok")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can speak dutch and english")
                .experience("2 years with Manchester City")
                .build();
        PlayerEntity player = PlayerEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        expected.add(player);
        expected.add(coach);

        when(this.userRepository.findAll()).thenReturn(expected);
        List<User> actual = this.userService.getAllUser(Coach.class);

        verify(userRepository, times(1)).findAll();
        assertEquals(1,actual.size());
    }

    @Test
    void getAllUser_Undefined_Class() {

        List<User> actual = this.userService.getAllUser(PlayerEntity.class);

        verify(userRepository, times(0)).findAll();
        assertTrue(actual.isEmpty());
    }
    @Test
    void getAllUser_With_List_Return_Null() {

        List<User> actual = this.userService.getAllUser(CoachEntity.class);

        verify(userRepository, times(0)).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void getUserByID_PLayer() {
        PlayerEntity expect = PlayerEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.userAuthorization).authorize(12);

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));

        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        Optional<User> user = this.userService.getUserByID(12,Player.class);
        Player actual = (Player) user.orElse(null);

        assert actual != null;
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getUsername()).isEqualTo(expect.getUsername());
        assertThat(actual.getGmail()).isEqualTo(expect.getGmail());
        assertThat(actual.getDateOfBirth()).isEqualTo(expect.getDateOfBirth());
        assertThat(actual.getPlayingLocation()).isEqualTo(expect.getPlayingLocation());
        assertThat(actual.getPosition()).isEqualTo(expect.getPosition());
    }

    @Test
    void getUserByID_Coach() {
        CoachEntity expect = CoachEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombok")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can speak dutch and english")
                .experience("2 years with Manchester City")
                .build();

        doNothing().when(this.userAuthorization).authorize(12);

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));

        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        Optional<User> user = this.userService.getUserByID(12,Coach.class);
        Coach actual = (Coach) user.orElse(null);

        assert actual != null;
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getUsername()).isEqualTo(expect.getUsername());
        assertThat(actual.getGmail()).isEqualTo(expect.getGmail());
        assertThat(actual.getDateOfBirth()).isEqualTo(expect.getDateOfBirth());
        assertThat(actual.getPlayingLocation()).isEqualTo(expect.getPlayingLocation());
        assertThat(actual.getDescription()).isEqualTo(expect.getDescription());
        assertThat(actual.getExperience()).isEqualTo(expect.getExperience());
    }

    @Test
    void getUserByID_Admin() {
        AdminEntity expect = AdminEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombok")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();

        doNothing().when(this.userAuthorization).authorize(12);

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));

        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        Optional<User> user = this.userService.getUserByID(12,Admin.class);
        Admin actual = (Admin) user.orElse(null);

        assert actual != null;
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getUsername()).isEqualTo(expect.getUsername());
        assertThat(actual.getGmail()).isEqualTo(expect.getGmail());
        assertThat(actual.getDateOfBirth()).isEqualTo(expect.getDateOfBirth());
        assertThat(actual.getPlayingLocation()).isEqualTo(expect.getPlayingLocation());
    }
    @Test
    void getUserByID_notPresent(){
        doNothing().when(this.userAuthorization).authorize(12);
        when(this.userRepository.findById(12)).thenReturn(Optional.empty());

        doNothing().when(this.userValidation).checkingUserExist(Optional.empty());

        Optional<User> optionalUser = this.userService.getUserByID(12,Player.class);

        assertEquals(optionalUser,Optional.empty());
    }
    @Test
    void getUserByID_ShouldThrowIdNotFound(){
        when(this.userRepository.findById(anyInt())).thenReturn(Optional.empty());

        doThrow(IdNotfoundException.class).when(this.userValidation).checkingUserExist(Optional.empty());

        assertThrows(IdNotfoundException.class, () -> this.userService.getUserByID(122, Player.class));
    }

    @Test
    void getUserById_instanceOfPlayerEntity_Not_ClassType(){
        PlayerEntity expect = PlayerEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.userAuthorization).authorize(12);

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));

        Optional<User> optionalUser = this.userService.getUserByID(12,Coach.class);

        assertEquals(optionalUser,Optional.empty());
    }
    @Test
    void getUserById_instanceOfCoachEntity_Not_ClassType(){
        CoachEntity expect = CoachEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombok")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can speak dutch and english")
                .experience("2 years with Manchester City")
                .build();


        doNothing().when(this.userAuthorization).authorize(12);

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));

        Optional<User> optionalUser = this.userService.getUserByID(12,Player.class);

        assertEquals(optionalUser,Optional.empty());
    }

    @Test
    void getUserById_instanceOfAdminEntity_Not_ClassType(){
        AdminEntity expect = AdminEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();

        doNothing().when(this.userAuthorization).authorize(12);

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));

        Optional<User> optionalUser = this.userService.getUserByID(12,Coach.class);

        assertEquals(optionalUser,Optional.empty());
    }

    @Test
    void deletePlayerById() {
        doNothing().when(this.userAuthorization).authorize(12);
        doNothing().when(this.userValidation).checkingUserExistWithID(12);
        doNothing().when(this.userRepository).deleteById(12);

        this.userService.deleteUserById(12);

        verify(userRepository, times(1)).deleteById(12);
    }

    @Test
    void createPlayer() {
        PlayerRequest playerRequest = PlayerRequest.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        PlayerEntity expect = PlayerEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        doNothing().when(this.userValidation).checkingValidGmail(anyString());
        doNothing().when(this.userValidation).checkingValidUsername(anyString());

        doNothing().when(this.userValidation).checkingUserGmailExist(anyString());
        doNothing().when(this.userValidation).checkingUserUsernameExist(anyString());

        when(this.passwordEncoder.encode(anyString())).thenReturn("usbfkvjshdbfv");

        when(this.playerRepository.save(Mockito.any(PlayerEntity.class))).thenReturn(expect);

        this.userService.createPlayer(playerRequest);

        verify(playerRepository, times(1)).save(Mockito.any(PlayerEntity.class));

    }

    @Test
    void createCoach() {
        CoachRequest coachRequest = CoachRequest.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        CoachEntity expect = CoachEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        doNothing().when(this.userValidation).checkingValidGmail(anyString());
        doNothing().when(this.userValidation).checkingValidUsername(anyString());

        doNothing().when(this.userValidation).checkingUserGmailExist(anyString());
        doNothing().when(this.userValidation).checkingUserUsernameExist(anyString());

        when(this.passwordEncoder.encode(anyString())).thenReturn("usbfkvjshdbfv");

        when(this.coachRepository.save(Mockito.any(CoachEntity.class))).thenReturn(expect);

        this.userService.createCoach(coachRequest);

        verify(coachRepository, times(1)).save(Mockito.any(CoachEntity.class));
    }

    @Test
    void createAdmin() {
        AdminRequest adminRequest = AdminRequest.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .workingLocation(PlayingLocation.EINDHOVEN)
                .build();
        AdminEntity expect = AdminEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();
        doNothing().when(this.userValidation).checkingValidGmail(anyString());
        doNothing().when(this.userValidation).checkingValidUsername(anyString());

        doNothing().when(this.userValidation).checkingUserGmailExist(anyString());
        doNothing().when(this.userValidation).checkingUserUsernameExist(anyString());

        when(this.passwordEncoder.encode(anyString())).thenReturn("usbfkvjshdbfv");

        when(this.adminRepository.save(Mockito.any(AdminEntity.class))).thenReturn(expect);

        this.userService.createAdmin(adminRequest);

        verify(adminRepository, times(1)).save(Mockito.any(AdminEntity.class));
    }

    @Test
    void createPlayer_GmailShouldThrowBlankInput(){
        PlayerRequest playerRequest = PlayerRequest.builder()
                .username("Jakacc")
                .gmail("")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        doThrow(BlankInput.class).when(this.userValidation).checkingValidGmail(playerRequest.getGmail());

        assertThrows(BlankInput.class, () -> this.userService.createPlayer(playerRequest));
    }

    @Test
    void createPlayer_UsernameShouldThrowBlankInput(){
        PlayerRequest playerRequest = PlayerRequest.builder()
                .username("")
                .gmail("")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        doThrow(BlankInput.class).when(this.userValidation).checkingValidUsername(playerRequest.getUsername());

        assertThrows(BlankInput.class, () -> this.userService.createPlayer(playerRequest));
    }

    @Test
    void createPlayer_ShouldThrowGmailAlreadyExist(){
        PlayerRequest playerRequest = PlayerRequest.builder()
                .username("hana21")
                .gmail("hana@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        doThrow(GmailAlreadyExist.class).when(this.userValidation).checkingUserGmailExist(playerRequest.getGmail());

        assertThrows(GmailAlreadyExist.class, () -> this.userService.createPlayer(playerRequest));
    }

    @Test
    void createPlayer_ShouldThrowUsernameAlreadyExist(){
        PlayerRequest playerRequest = PlayerRequest.builder()
                .username("hana21")
                .gmail("hana@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        doThrow(UsernameAlreadyExist.class).when(this.userValidation).checkingUserUsernameExist(playerRequest.getUsername());

        assertThrows(UsernameAlreadyExist.class, () -> this.userService.createPlayer(playerRequest));
    }

    @Test
    void createCoach_GmailShouldThrowBlankInput(){
        CoachRequest coachRequest = CoachRequest.builder()
                .username("Jakacc")
                .gmail("")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        doThrow(BlankInput.class).when(this.userValidation).checkingValidGmail(coachRequest.getGmail());

        assertThrows(BlankInput.class, () -> this.userService.createCoach(coachRequest));
    }

    @Test
    void createCoach_UsernameShouldThrowBlankInput(){
        CoachRequest coachRequest = CoachRequest.builder()
                .username("")
                .gmail("")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        doThrow(BlankInput.class).when(this.userValidation).checkingValidUsername(coachRequest.getUsername());

        assertThrows(BlankInput.class, () -> this.userService.createCoach(coachRequest));
    }

    @Test
    void createCoach_ShouldThrowGmailAlreadyExist(){
        CoachRequest coachRequest = CoachRequest.builder()
                .username("hana21")
                .gmail("hana@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        doThrow(GmailAlreadyExist.class).when(this.userValidation).checkingUserGmailExist(coachRequest.getGmail());

        assertThrows(GmailAlreadyExist.class, () -> this.userService.createCoach(coachRequest));
    }

    @Test
    void createCoach_ShouldThrowUsernameAlreadyExist(){
        CoachRequest coachRequest = CoachRequest.builder()
                .username("hana21")
                .gmail("hana@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        doThrow(UsernameAlreadyExist.class).when(this.userValidation).checkingUserUsernameExist(coachRequest.getUsername());

        assertThrows(UsernameAlreadyExist.class, () -> this.userService.createCoach(coachRequest));
    }
    @Test
    void createAdmin_GmailShouldThrowBlankInput(){
        AdminRequest adminRequest = AdminRequest.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .workingLocation(PlayingLocation.EINDHOVEN)
                .build();
        doThrow(BlankInput.class).when(this.userValidation).checkingValidGmail(adminRequest.getGmail());

        assertThrows(BlankInput.class, () -> this.userService.createAdmin(adminRequest));
    }
    @Test
    void createAdmin_UsernameShouldThrowBlankInput(){
        AdminRequest adminRequest = AdminRequest.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .workingLocation(PlayingLocation.EINDHOVEN)
                .build();
        doThrow(BlankInput.class).when(this.userValidation).checkingValidUsername(adminRequest.getUsername());

        assertThrows(BlankInput.class, () -> this.userService.createAdmin(adminRequest));
    }

    @Test
    void createAdmin_ShouldThrowGmailAlreadyExist(){
        AdminRequest adminRequest = AdminRequest.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .workingLocation(PlayingLocation.EINDHOVEN)
                .build();
        doThrow(GmailAlreadyExist.class).when(this.userValidation).checkingUserGmailExist(adminRequest.getGmail());

        assertThrows(GmailAlreadyExist.class, () -> this.userService.createAdmin(adminRequest));
    }

    @Test
    void createAdmin_ShouldThrowUsernameAlreadyExist(){
        AdminRequest adminRequest = AdminRequest.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .workingLocation(PlayingLocation.EINDHOVEN)
                .build();
        doThrow(UsernameAlreadyExist.class).when(this.userValidation).checkingUserUsernameExist(adminRequest.getUsername());

        assertThrows(UsernameAlreadyExist.class, () -> this.userService.createAdmin(adminRequest));
    }

    @Test
    void updateUser_Player() {
        Player player = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        PlayerEntity expect = PlayerEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        this.userService.updateUser(player);
        verify(playerRepository, times(1)).updatePlayer(anyInt(),
                anyString(),
                anyString(),
                any(),
                any(),
                any());
    }
    @Test
    void updateUser_Coach() {
        Coach coach = Coach.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        CoachEntity expect = CoachEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        this.userService.updateUser(coach);
        verify(coachRepository, times(1)).updateCoach(anyInt(),
                anyString(),
                anyString(),
                any(),
                any(),
                any(),
                any());
    }
    @Test
    void updateUser_GmailShouldThrowBlankInput(){
        Player player = Player.builder()
                .id(12)
                .username("")
                .gmail("")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doThrow(BlankInput.class).when(this.userValidation).checkingValidGmail(player.getGmail());

        assertThrows(BlankInput.class, () -> this.userService.updateUser(player));
    }

    @Test
    void updateUser_UsernameShouldThrowBlankInput(){
        Player player = Player.builder()
                .id(12)
                .username("")
                .gmail("")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doThrow(BlankInput.class).when(this.userValidation).checkingValidUsername(player.getGmail());

        assertThrows(BlankInput.class, () -> this.userService.updateUser(player));
    }

    @Test
    void updateUser_ShouldThrowIdNotFound(){
        Player expect = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        when(this.userRepository.findById(expect.getId())).thenReturn(Optional.empty());

        doThrow(IdNotfoundException.class).when(this.userValidation).checkingUserExist(Optional.empty());

        assertThrows(IdNotfoundException.class, () -> this.userService.updateUser(expect));
    }

    @Test
    void updateUser_ShouldThrowUsernameAlreadyExist(){
        Player expect = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        PlayerEntity duplicate = PlayerEntity.builder()
                .id(122)
                .username("Jakacc")
                .gmail("jackie123@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        List<UserEntity> list = new ArrayList<>();
        list.add(duplicate);

        when(this.userRepository.getByUsernameAndGmailIgnoreIdCase(anyString(),anyString(),anyInt())).thenReturn(list);

        doThrow(UsernameAlreadyExist.class).when(this.userValidation).checkingDuplicateExist(list,expect);

        assertThrows(UsernameAlreadyExist.class, () -> this.userService.updateUser(expect));
    }

    @Test
    void updateUser_ShouldThrowGmailAlreadyExist(){
        Player expect = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        PlayerEntity duplicate = PlayerEntity.builder()
                .id(122)
                .username("Jakacc123")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        List<UserEntity> list = new ArrayList<>();
        list.add(duplicate);

        when(this.userRepository.getByUsernameAndGmailIgnoreIdCase(anyString(),anyString(),anyInt())).thenReturn(list);

        doThrow(GmailAlreadyExist.class).when(this.userValidation).checkingDuplicateExist(list,expect);

        assertThrows(GmailAlreadyExist.class, () -> this.userService.updateUser(expect));
    }


    @Test
    void updateUser_instancePlayer(){
        Player player = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        PlayerEntity expect = PlayerEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        doNothing().when(this.playerRepository).updatePlayer(player.getId(),player.getGmail(),
                player.getUsername(),
                player.getDateOfBirth(),
                player.getPlayingLocation(),
                player.getPosition());

        this.userService.updateUser(player);

        verify(playerRepository, times(1)).updatePlayer(anyInt(),
                anyString(),
                anyString(),
                any(),
                any(),
                any());


    }

    @Test
    void updateUser_instanceCoach(){
        Coach coach = Coach.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        CoachEntity expect = CoachEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        doNothing().when(this.coachRepository).updateCoach(coach.getId(),coach.getGmail(),
                coach.getUsername(),
                coach.getDateOfBirth(),
                coach.getPlayingLocation(),
                coach.getDescription(),
                coach.getExperience());

        this.userService.updateUser(coach);

        verify(coachRepository, times(1)).updateCoach(anyInt(),
                anyString(),
                anyString(),
                any(),
                any(),
                any(),any());
    }

    @Test
    void updateUser_userEntity_Not_Present(){
        Coach coach = Coach.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        CoachEntity expect = CoachEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.empty());

        this.userService.updateUser(coach);

        verify(coachRepository, times(0)).updateCoach(anyInt(),
                anyString(),
                anyString(),
                any(),
                any(),
                any(),any());;

    }
    @Test
    void updateUser_instanceAdmin(){
        Admin admin = Admin.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();
        AdminEntity expect = AdminEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        doNothing().when(this.adminRepository).updateAdmin(admin.getId(),admin.getGmail(),
                admin.getUsername(),
                admin.getDateOfBirth(),
                admin.getPlayingLocation());

        this.userService.updateUser(admin);

        verify(adminRepository, times(1)).updateAdmin(anyInt(),
                anyString(),
                anyString(),
                any(),
                any());
    }

    @Test
    void updateUser_instanceCoach_Not(){
        Coach coach = Coach.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        Admin admin = Admin.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();
        Player player = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        PlayerEntity expect = PlayerEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        this.userService.updateUser(coach);

        verify(userValidation, times(1)).checkingValidUsername(anyString());
    }

    @Test
    void updateUser_instancePlayer_Not(){
        Admin admin = Admin.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();
        Player player = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        CoachEntity expect = CoachEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .experience("")
                .description("")
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        this.userService.updateUser(player);

        verify(userValidation, times(1)).checkingValidUsername(anyString());
    }


    @Test
    void updateUser_instanceAdmin_Not(){
        Coach coach = Coach.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can do all thing")
                .experience("1 year with SOLID")
                .build();
        Admin admin = Admin.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .build();
        Player player = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        PlayerEntity expect = PlayerEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        doNothing().when(this.userValidation).checkingValidUsername(anyString());
        doNothing().when(this.userValidation).checkingValidGmail(anyString());

        when(this.userRepository.findById(12)).thenReturn(Optional.of(expect));
        doNothing().when(this.userValidation).checkingUserExist(Optional.of(expect));

        this.userService.updateUser(admin);

        verify(userValidation, times(1)).checkingValidUsername(anyString());
    }



}
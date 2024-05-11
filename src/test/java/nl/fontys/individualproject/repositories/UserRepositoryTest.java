//package nl.fontys.individualproject.repositories;
//
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import nl.fontys.individualproject.domain.enume.PlayingLocation;
//import nl.fontys.individualproject.domain.enume.Position;
//import nl.fontys.individualproject.repositories.Entity.CoachEntity;
//import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
//import nl.fontys.individualproject.repositories.Entity.UserEntity;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Transactional
//@DataJpaTest
//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UserRepositoryTest {
//
//    @Autowired
//    private EntityManager entityManger;
//    @Autowired
//    private UserRepository userRepository;
//
//    private PlayerEntity playerEntity;
//    private CoachEntity coachEntity;
//
//    @BeforeEach
//    void setUp() {
//        playerEntity = PlayerEntity.builder()
//                .username("Jakacc")
//                .gmail("jackie@gmail.com")
//                .password("lombox")
//                .dateOfBirth(LocalDate.of(2002,9,9))
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .position(Position.FORWARD)
//                .build();
//        coachEntity = CoachEntity.builder()
//                  .username("tommy")
//                   .gmail("tmmy@gmail.com")
//                  .password("tm34")
//                  .dateOfBirth(LocalDate.of(1983, 9,12))
//                  .playingLocation(PlayingLocation.EINDHOVEN)
//                  .description("I can speak english")
//                  .experience("2 years with Manchester United")
//                  .build();
//        entityManger.persist(playerEntity);
//        entityManger.persist(coachEntity);
//        entityManger.flush();
//    }
//
//    @AfterEach
//    void tearDown() {
//        entityManger.clear();
//    }
//
//    @Test
//    void existsByGmail() {
//        boolean coachResult = this.userRepository.existsByGmail(coachEntity.getGmail());
//        boolean playerResult = this.userRepository.existsByGmail(playerEntity.getGmail());
//
//        assertTrue(coachResult);
//        assertTrue(playerResult);
//    }
//
//    @Test
//    void not_existsByGmail() {
//        boolean coachResult = this.userRepository.existsByGmail("woacom@gmail.com");
//        boolean playerResult = this.userRepository.existsByGmail("woakom@gmail.com");
//
//        assertFalse(coachResult);
//        assertFalse(playerResult);
//    }
//
//    @Test
//    void existsByUsername() {
//        boolean coachResult = this.userRepository.existsByUsername(coachEntity.getUsername());
//        boolean playerResult = this.userRepository.existsByUsername(playerEntity.getUsername());
//
//        assertTrue(coachResult);
//        assertTrue(playerResult);
//    }
//
//    @Test
//    void not_existsByUsername() {
//        boolean coachResult = this.userRepository.existsByUsername("wokimo");
//        boolean playerResult = this.userRepository.existsByUsername("mockito");
//
//        assertFalse(coachResult);
//        assertFalse(playerResult);
//    }
//
//    @Test
//    void getByUsernameAndGmailIgnoreIdCase() {
//        PlayerEntity playerToIgnoreCase = entityManger.find(PlayerEntity.class,playerEntity.getId());
//        List<UserEntity> userEntities = this.userRepository.getByUsernameAndGmailIgnoreIdCase(coachEntity.getUsername(), coachEntity.getGmail(), playerToIgnoreCase.getId());
//        assertEquals(1,userEntities.size());
//    }
//
//    @Test
//    void getByUsernameAndGmailIgnoreIdCase_IdDoesNotExist() {
//        List<UserEntity> userEntities = this.userRepository.getByUsernameAndGmailIgnoreIdCase(coachEntity.getUsername(), playerEntity.getGmail(), 12);
//        assertEquals(2,userEntities.size());
//    }
//
//    @Test
//    void getByUsernameAndGmailIgnoreIdCase_CredentialDoesNotExist() {
//        List<UserEntity> userEntities = this.userRepository.getByUsernameAndGmailIgnoreIdCase("alma", "alama@gmail.com", 13);
//        assertEquals(0,userEntities.size());
//    }
//}
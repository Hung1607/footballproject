//package nl.fontys.individualproject.repositories;
//
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import nl.fontys.individualproject.domain.enume.PlayingLocation;
//import nl.fontys.individualproject.repositories.Entity.CoachEntity;
//import org.hibernate.annotations.Generated;
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
//
//import static org.junit.jupiter.api.Assertions.*;
//@Transactional
//@DataJpaTest
//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class CoachRepositoryTest {
//
//    @Autowired
//    private EntityManager entityManger;
//    @Autowired
//    private CoachRepository coachRepository;
//    private CoachEntity coachEntity;
//
//    @BeforeEach
//    void setUp() {
//        coachEntity = CoachEntity.builder()
//                .id(12)
//                .username("Jakacc")
//                .gmail("jackie@gmail.com")
//                .password("lombok")
//                .dateOfBirth(LocalDate.of(2002,9,9))
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .description("I can speak dutch and english")
//                .experience("2 years with Manchester City")
//                .status("PENDING")
//                .build();
//        entityManger.persist(coachEntity); //It is a way to tell the JPA provider (e.g., Hibernate) that you want to save the entity to the database.
//        entityManger.flush();
//    }
//
//    @AfterEach
//    void tearDown() {
//        entityManger.clear();
//    }
//
//    @Test
//    void updateCoach() {
//        CoachEntity expected = CoachEntity.builder()
//                .id(12)
//                .username("Jakacccocm")
//                .gmail("jackie123@gmail.com")
//                .password("lombok")
//                .dateOfBirth(LocalDate.of(2002,9,9))
//                .playingLocation(PlayingLocation.AMSTERDAM)
//                .experience("2 years with Manchester City")
//                .description("I can speak dutch and english")
//                .status("PENDING")
//                .build();
//
//
//        this.coachRepository.updateCoach(expected.getId(),
//                expected.getGmail(),
//                expected.getUsername(),
//                expected.getDateOfBirth(),
//                expected.getPlayingLocation(),
//                expected.getDescription(),
//                expected.getExperience());
//        // force synchronize
//        entityManger.flush();
//        entityManger.clear();
//
//        CoachEntity actual = entityManger.find(CoachEntity.class,expected.getId());
//
//        assertEquals(actual.getId(),expected.getId());
//        assertEquals(actual.getGmail(),expected.getGmail());
//        assertEquals(actual.getUsername(),expected.getUsername());
//        assertEquals(actual.getPlayingLocation(),expected.getPlayingLocation());
//        assertEquals(actual.getDescription(),expected.getDescription());
//        assertEquals(actual.getExperience(),expected.getExperience());
//    }
//
//    @Test
//    void updateCoach_IdDoesNotExist() {
//        CoachEntity expected = CoachEntity.builder()
//                .id(123123)
//                .username("Jakacccocm")
//                .gmail("jackie123@gmail.com")
//                .dateOfBirth(LocalDate.of(2002,9,9))
//                .playingLocation(PlayingLocation.AMSTERDAM)
//                .experience("2 years with Manchester United")
//                .description("I can speak dutch")
//                .build();
//
//        this.coachRepository.updateCoach(expected.getId(),
//                expected.getGmail(),
//                expected.getUsername(),
//                expected.getDateOfBirth(),
//                expected.getPlayingLocation(),
//                expected.getDescription(),
//                expected.getExperience());
//
//        CoachEntity actual = entityManger.find(CoachEntity.class,coachEntity.getId());
//
//        assertNotEquals(actual.getId(),expected.getId());
//        assertNotEquals(actual.getGmail(),expected.getGmail());
//        assertNotEquals(actual.getUsername(),expected.getUsername());
//        assertNotEquals(actual.getPlayingLocation(),expected.getPlayingLocation());
//        assertNotEquals(actual.getDescription(),expected.getDescription());
//        assertNotEquals(actual.getExperience(),expected.getExperience());
//    }
//}
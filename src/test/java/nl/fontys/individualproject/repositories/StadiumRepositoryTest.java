//package nl.fontys.individualproject.repositories;
//
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import nl.fontys.individualproject.domain.enume.PlayingLocation;
//import nl.fontys.individualproject.domain.enume.SurfaceType;
//import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class StadiumRepositoryTest {
//    private StadiumEntity stadiumEntity;
//    @Autowired
//    private EntityManager entityManager;
//    @Autowired
//    private StadiumRepository stadiumRepository;
//
//    @BeforeEach
//    void setUp() {
//         stadiumEntity = StadiumEntity.builder()
//                 .name("Allianz Arena")
//                 .address("Werner")
//                 .playingLocation(PlayingLocation.AMSTERDAM)
//                 .capacity(22)
//                 .surfaceType(SurfaceType.NaturalTurf)
//                 .build();
//         entityManager.persist(stadiumEntity); // insert object to db
//         entityManager.flush();
//    }
//
//    @AfterEach
//    void tearDown() {
//         entityManager.clear();
//    }
//
//    @Test
//    void existsByName() {
//        boolean result = stadiumRepository.existsByName(stadiumEntity.getName());
//        assertTrue(result);
//    }
//
//    @Test
//    void Not_ExistsByName(){
//        boolean result = stadiumRepository.existsByName("Jackie Jan");
//        assertFalse(result);
//    }
//
//    @Test
//    void existsByAddress() {
//        boolean result = stadiumRepository.existsByAddress(stadiumEntity.getAddress());
//        assertTrue(result);
//    }
//
//    @Test
//    void Not_ExistsByAddress(){
//        boolean result = stadiumRepository.existsByAddress("Jackie Jan");
//        assertFalse(result);
//    }
//
//    @Test
//    void getByNameAndAddressIgnoreIdCase() {
//        StadiumEntity savedStadiumEntity = this.stadiumRepository.save(StadiumEntity.builder()
//                .name("Alloniaza")
//                .address("Kolaplaper")
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .capacity(10)
//                .surfaceType(SurfaceType.NaturalTurf)
//                .build());
//        StadiumEntity savedStadiumEntity1 = this.stadiumRepository.save(StadiumEntity.builder()
//                .name("Kapspel")
//                .address("Lolipop")
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .capacity(10)
//                .surfaceType(SurfaceType.NaturalTurf)
//                .build());
//        StadiumEntity savedStadiumEntity2 = this.stadiumRepository.save(StadiumEntity.builder()
//                .name("Kapsper")
//                .address("Lolipo000p")
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .capacity(10)
//                .surfaceType(SurfaceType.NaturalTurf)
//                .build());
//        List<StadiumEntity> expectedStadiumEntity = this.stadiumRepository.getByNameAndAddressIgnoreIdCase("Kapspel",
//                                                                                                "Lolipo000p",
//                                                                                                savedStadiumEntity.getId());
//        assertEquals(2,expectedStadiumEntity.size());
//    }
//
//    @Test
//    void test_GetByNameAndAddressIgnoreIdCase_IdNotExists(){
//        StadiumEntity savedStadiumEntity = this.stadiumRepository.save(StadiumEntity.builder()
//                .name("Alloniaza")
//                .address("Kolaplaper")
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .capacity(10)
//                .surfaceType(SurfaceType.NaturalTurf)
//                .build());
//        StadiumEntity savedStadiumEntity1 = this.stadiumRepository.save(StadiumEntity.builder()
//                .name("Kapspel")
//                .address("Lolipop")
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .capacity(10)
//                .surfaceType(SurfaceType.NaturalTurf)
//                .build());
//        StadiumEntity savedStadiumEntity2 = this.stadiumRepository.save(StadiumEntity.builder()
//                .name("Kapsper")
//                .address("Lolipo000p")
//                .playingLocation(PlayingLocation.EINDHOVEN)
//                .capacity(10)
//                .surfaceType(SurfaceType.NaturalTurf)
//                .build());
//        List<StadiumEntity> expectedStadiumEntity = this.stadiumRepository.getByNameAndAddressIgnoreIdCase("Kapspel",
//                "Lolipo000p",
//                1);
//        assertEquals(2,expectedStadiumEntity.size());
//    }
//}
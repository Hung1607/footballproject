package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerConvertTest {


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convert() {
        PlayerEntity expect = PlayerEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .password("lombox")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();

        Player actual = PlayerConvert.convert(expect);

        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getUsername()).isEqualTo(expect.getUsername());
        assertThat(actual.getGmail()).isEqualTo(expect.getGmail());
        assertThat(actual.getDateOfBirth()).isEqualTo(expect.getDateOfBirth());
        assertThat(actual.getPlayingLocation()).isEqualTo(expect.getPlayingLocation());
        assertThat(actual.getPosition()).isEqualTo(expect.getPosition());
    }

    @Test
    void convert_FromNull() {
        PlayerEntity expect = new PlayerEntity();

        Player actual =  PlayerConvert.convert(expect);
        assertEquals(0,actual.getId());
        assertNull(actual.getUsername());
        assertNull(actual.getGmail());
        assertNull(actual.getDateOfBirth());
        assertNull(actual.getPlayingLocation());
        assertNull(actual.getPosition());
    }
}
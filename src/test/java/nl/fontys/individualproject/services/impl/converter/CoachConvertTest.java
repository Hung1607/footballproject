package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CoachConvertTest {

    @Test
    void convert() {
        CoachEntity expect = CoachEntity.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .description("I can speak dutch and english")
                .experience("2 years with Manchester City")
                .build();

        Coach actual =  CoachConvert.convert(expect);
        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getUsername()).isEqualTo(expect.getUsername());
        assertThat(actual.getGmail()).isEqualTo(expect.getGmail());
        assertThat(actual.getDateOfBirth()).isEqualTo(expect.getDateOfBirth());
        assertThat(actual.getPlayingLocation()).isEqualTo(expect.getPlayingLocation());
        assertThat(actual.getDescription()).isEqualTo(expect.getDescription());
        assertThat(actual.getExperience()).isEqualTo(expect.getExperience());
    }

    @Test
    void convert_FromNull() {
        CoachEntity expect = new CoachEntity();

        Coach actual =  CoachConvert.convert(expect);
        assertEquals(0,actual.getId());
        assertNull(actual.getUsername());
        assertNull(actual.getGmail());
        assertNull(actual.getDateOfBirth());
        assertNull(actual.getPlayingLocation());
        assertNull(actual.getDescription());
        assertNull(actual.getExperience());
    }
}
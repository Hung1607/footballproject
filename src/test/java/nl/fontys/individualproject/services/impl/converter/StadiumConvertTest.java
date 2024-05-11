package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.SurfaceType;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class StadiumConvertTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convert() {
        StadiumEntity expected = StadiumEntity.builder()
                .id(1)
                .name("Alloniasdcaza")
                .address("Kolaplaasdcperizer")
                .playingLocation(PlayingLocation.EINDHOVEN)
                .capacity(10)
                .surfaceType(SurfaceType.NaturalTurf)
                .build();
        Stadium actual = StadiumConvert.convert(expected);

        assertThat(expected.getId()).isSameAs(actual.getId());
        assertThat(expected.getName()).isSameAs(actual.getName());
        assertThat(expected.getAddress()).isSameAs(actual.getAddress());
        assertThat(expected.getPlayingLocation()).isSameAs(actual.getPlayingLocation());
        assertThat(expected.getCapacity()).isSameAs(actual.getCapacity());
        assertThat(expected.getSurfaceType()).isSameAs(actual.getSurfaceType());
    }

    @Test
    void convert_FromNull() {
        StadiumEntity expect = new StadiumEntity();

        Stadium actual =  StadiumConvert.convert(expect);
        assertEquals(0,actual.getId());
        assertNull(actual.getName());
        assertNull(actual.getAddress());
        assertNull(actual.getPlayingLocation());
        assertEquals(actual.getCapacity(),0);
        assertNull(actual.getSurfaceType());
    }
}
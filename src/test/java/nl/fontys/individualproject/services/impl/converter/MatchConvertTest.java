package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;
import nl.fontys.individualproject.domain.enume.SurfaceType;
import nl.fontys.individualproject.domain.response.Match;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.MatchEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MatchConvertTest {

    @Test
    void convert() {
        MatchEntity expect = MatchEntity.builder()
                .id(12)
                .date(LocalDate.of(2023,9,10))
                .time(LocalTime.of(11, 0, 0))
                .time_end(LocalTime.of(12, 0, 0))
                .players(new ArrayList<>())
                .stadium(StadiumEntity.builder()
                        .name("asd")
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .address("wads")
                        .capacity(12)
                        .surfaceType(SurfaceType.NaturalTurf).build())
                .players(new ArrayList<>())
                .coach(CoachEntity.builder()
                        .username("Jakacc")
                        .gmail("jackie@gmail.com")
                        .dateOfBirth(LocalDate.of(2002,9,9))
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .description("I can speak dutch and english")
                        .experience("2 years with Manchester City")
                        .build())
                .build();
        expect.getPlayers().add(PlayerEntity.builder()
                .id(13)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build());

        Match actual = MatchConvert.convert(expect);

        assertThat(actual.getId()).isEqualTo(expect.getId());
        assertThat(actual.getTime()).isEqualTo(expect.getTime());
        assertThat(actual.getTime_end()).isEqualTo(expect.getTime_end());
        assertThat(actual.getDate()).isEqualTo(expect.getDate());
    }
}
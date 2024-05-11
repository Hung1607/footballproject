package nl.fontys.individualproject.services.impl;

import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;
import nl.fontys.individualproject.domain.enume.SurfaceType;
import nl.fontys.individualproject.domain.request.CoachTotalMatchRequest;
import nl.fontys.individualproject.domain.request.MatchRequest;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.Match;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.MatchEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import nl.fontys.individualproject.repositories.MatchRepository;
import nl.fontys.individualproject.services.exception.BlankInput;
import nl.fontys.individualproject.services.exception.DateTimeInvalid;
import nl.fontys.individualproject.services.exception.MatchIDNotFound;
import nl.fontys.individualproject.services.validation.match.MatchValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private MatchValidation matchValidation;

    @InjectMocks
    private MatchService matchService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createMatch() {
        MatchRequest matchRequest = MatchRequest.builder()
                .date(LocalDate.of(2023,9,10))
                .time(LocalTime.of(11, 0, 0))
                .time_end(LocalTime.of(12, 0, 0))
                .stadium(Stadium.builder()
                        .name("asd")
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .address("wads")
                        .capacity(12)
                        .surfaceType(SurfaceType.NaturalTurf).build())
                .coach(Coach.builder()
                        .username("Jakacc")
                        .gmail("jackie@gmail.com")
                        .dateOfBirth(LocalDate.of(2002,9,9))
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .description("I can speak dutch and english")
                        .experience("2 years with Manchester City")
                        .build())
                .build();
        MatchEntity matchEntity = MatchEntity.builder()
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
                        .coach(CoachEntity.builder()
                                .username("Jakacc")
                                .gmail("jackie@gmail.com")
                                .dateOfBirth(LocalDate.of(2002,9,9))
                                .playingLocation(PlayingLocation.EINDHOVEN)
                                .description("I can speak dutch and english")
                                .experience("2 years with Manchester City")
                                .build())
                .build();

        when(this.matchRepository.getMatchEntitiesByDate(any())).thenReturn(Collections.emptyList());

        doNothing().when(this.matchValidation).checkingTimeRequest(matchRequest,Collections.emptyList());

        when(this.matchRepository.save(any(MatchEntity.class))).thenReturn(matchEntity);

        this.matchService.createMatch(matchRequest);

        verify(matchRepository,times(1) ).save(any(MatchEntity.class));
    }

    @Test
    void  createMatch_throw_DateTimeInvalid(){
        MatchRequest matchRequest = MatchRequest.builder()
                    .date(LocalDate.of(2023,9,10))
                    .time(LocalTime.of(11, 0, 0))
                    .time_end(LocalTime.of(12, 0, 0))
                    .stadium(Stadium.builder()
                            .name("asd")
                            .playingLocation(PlayingLocation.EINDHOVEN)
                            .address("wads")
                            .capacity(12)
                            .surfaceType(SurfaceType.NaturalTurf).build())
                    .coach(Coach.builder()
                            .username("Jakacc")
                            .gmail("jackie@gmail.com")
                            .dateOfBirth(LocalDate.of(2002,9,9))
                            .playingLocation(PlayingLocation.EINDHOVEN)
                            .description("I can speak dutch and english")
                            .experience("2 years with Manchester City")
                            .build())
                    .build();
        MatchEntity matchEntity = MatchEntity.builder()
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
                    .coach(CoachEntity.builder()
                            .username("Jakacc")
                            .gmail("jackie@gmail.com")
                            .dateOfBirth(LocalDate.of(2002,9,9))
                            .playingLocation(PlayingLocation.EINDHOVEN)
                            .description("I can speak dutch and english")
                            .experience("2 years with Manchester City")
                            .build())
                    .build();
        List<MatchEntity> matchEntities = new ArrayList<>();
        matchEntities.add(matchEntity);

        when(this.matchRepository.getMatchEntitiesByDate(any())).thenReturn(matchEntities);

        doThrow(DateTimeInvalid.class).when(this.matchValidation).checkingTimeRequest(matchRequest,matchEntities);

        assertThrows(DateTimeInvalid.class, () -> this.matchService.createMatch(matchRequest));
    }

    @Test
    void addPlayerList() {
        MatchEntity matchEntity = MatchEntity.builder()
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
                .coach(CoachEntity.builder()
                        .username("Jakacc")
                        .gmail("jackie@gmail.com")
                        .dateOfBirth(LocalDate.of(2002,9,9))
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .description("I can speak dutch and english")
                        .experience("2 years with Manchester City")
                        .build())
                .build();
        Player player = Player.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        when(this.matchRepository.findById(anyInt())).thenReturn(Optional.of(matchEntity));

        doNothing().when(this.matchValidation).checkingStadiumCapacity(matchEntity);
        doNothing().when(this.matchValidation).checkingDuplicatePlayer(player,matchEntity);

        when(this.matchRepository.save(Mockito.any(MatchEntity.class))).thenReturn(matchEntity);

        this.matchService.addPlayerList(12,player);
        verify(matchRepository,times(1) ).save(any(MatchEntity.class));
    }

    @Test
    void addPlayerList_Throw_MatchIDNotFound(){
        when(this.matchRepository.findById(anyInt())).thenReturn(Optional.empty());
        Player player = Player.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        assertThrows(MatchIDNotFound.class, () -> this.matchService.addPlayerList(12,player));
    }

    @Test
    void removePlayer_resultInside_false() {
        MatchEntity matchEntity = MatchEntity.builder()
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
                .coach(CoachEntity.builder()
                        .username("Jakacc")
                        .gmail("jackie@gmail.com")
                        .dateOfBirth(LocalDate.of(2002,9,9))
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .description("I can speak dutch and english")
                        .experience("2 years with Manchester City")
                        .build())
                .build();
        Player player = Player.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        when(this.matchRepository.findById(anyInt())).thenReturn(Optional.of(matchEntity));

        doNothing().when(this.matchValidation).checkingTimeAllowedToExit(matchEntity);
        doNothing().when(this.matchValidation).checkingDuplicatePlayer(player,matchEntity);

        when(this.matchValidation.checkingPlayerExistInMatch(player,matchEntity)).thenReturn(false);

        when(this.matchRepository.save(any(MatchEntity.class))).thenReturn(matchEntity);

        this.matchService.removePlayer(12,player);

        verify(matchRepository,times(1) ).save(any(MatchEntity.class));
    }
    @Test
    void removePlayer_resultInside_true() {
        Player player = Player.builder()
                 .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        MatchEntity matchEntity = MatchEntity.builder()
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
        matchEntity.getPlayers().add(PlayerEntity.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build());
        when(this.matchRepository.findById(anyInt())).thenReturn(Optional.of(matchEntity));

        doNothing().when(this.matchValidation).checkingTimeAllowedToExit(matchEntity);
        doNothing().when(this.matchValidation).checkingDuplicatePlayer(player,matchEntity);

        when(this.matchValidation.checkingPlayerExistInMatch(player,matchEntity)).thenReturn(true);

        when(this.matchRepository.save(any(MatchEntity.class))).thenReturn(matchEntity);

        this.matchService.removePlayer(12,player);

        verify(matchRepository,times(1) ).save(any(MatchEntity.class));
    }

    @Test
    void removePlayer_resultInside_true_but_Not_remove() {
        Player player = Player.builder()
                .id(12)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        MatchEntity matchEntity = MatchEntity.builder()
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
        matchEntity.getPlayers().add(PlayerEntity.builder()
                .id(13)
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build());
        when(this.matchRepository.findById(anyInt())).thenReturn(Optional.of(matchEntity));

        doNothing().when(this.matchValidation).checkingTimeAllowedToExit(matchEntity);
        doNothing().when(this.matchValidation).checkingDuplicatePlayer(player,matchEntity);

        when(this.matchValidation.checkingPlayerExistInMatch(player,matchEntity)).thenReturn(true);

        when(this.matchRepository.save(any(MatchEntity.class))).thenReturn(matchEntity);

        this.matchService.removePlayer(12,player);

        verify(matchRepository,times(1) ).save(any(MatchEntity.class));
    }

    @Test
    void removePlayer_Throw_MatchIDNotFound(){
        when(this.matchRepository.findById(anyInt())).thenReturn(Optional.empty());
        Player player = Player.builder()
                .username("Jakacc")
                .gmail("jackie@gmail.com")
                .dateOfBirth(LocalDate.of(2002,9,9))
                .playingLocation(PlayingLocation.EINDHOVEN)
                .position(Position.FORWARD)
                .build();
        assertThrows(MatchIDNotFound.class, () -> this.matchService.removePlayer(12,player));

    }
    @Test
    void getAllMatch() {
        MatchEntity matchEntity = MatchEntity.builder()
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
                .coach(CoachEntity.builder()
                        .username("Jakacc")
                        .gmail("jackie@gmail.com")
                        .dateOfBirth(LocalDate.of(2002,9,9))
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .description("I can speak dutch and english")
                        .experience("2 years with Manchester City")
                        .build())
                .build();
        List<MatchEntity> matchEntities = new ArrayList<>();
        matchEntities.add(matchEntity);
        when(this.matchRepository.findAll()).thenReturn(matchEntities);

        List<Match> matches = this.matchService.getAllMatch();

        assertEquals(1,matches.size());

    }

    @Test
    void getMatchByID() {
        MatchEntity matchEntity = MatchEntity.builder()
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
                .coach(CoachEntity.builder()
                        .username("Jakacc")
                        .gmail("jackie@gmail.com")
                        .dateOfBirth(LocalDate.of(2002,9,9))
                        .playingLocation(PlayingLocation.EINDHOVEN)
                        .description("I can speak dutch and english")
                        .experience("2 years with Manchester City")
                        .build())
                .build();
        when(this.matchRepository.findById(12)).thenReturn(Optional.of(matchEntity));

        Optional<Match> matchOptional = this.matchService.getMatchByID(12);
        matchOptional.ifPresent(Assertions::assertNotNull);
    }
    @Test
    void getMatchByID_Throw_MatchIDNotFound(){
        when(this.matchRepository.findById(12)).thenReturn(Optional.empty());

        assertThrows(MatchIDNotFound.class, () -> this.matchService.getMatchByID(12));
    }

    @Test
    void getTotalMatchByCoach(){
        // Arrange
        CoachTotalMatchRequest expectedRequest = CoachTotalMatchRequest.builder()
                .date_start(LocalDate.of(2024, 12, 1))
                .date_end(LocalDate.of(2024, 12, 9))
                .build();

        doNothing().when(this.matchValidation).checkingCoachTotalTimeRequest(expectedRequest);

        when(this.matchRepository.getTotalMatchByAllCoach(LocalDate.of(2024,12,1),LocalDate.of(2024,12,9))).thenReturn(Collections.emptyList());

        this.matchService.getTotalMatchByCoach(expectedRequest);

        verify(this.matchValidation,times(1)).checkingCoachTotalTimeRequest(any());
        verify(this.matchRepository,times(1)).getTotalMatchByAllCoach(any(),any());
    }

    @Test
    void getTotalMatchByCoach_throw_exception(){
        // Arrange
        CoachTotalMatchRequest expectedRequest = CoachTotalMatchRequest.builder()
                .date_start(LocalDate.of(2024, 12, 12))
                .date_end(LocalDate.of(2024, 12, 9))
                .build();
        doThrow(DateTimeInvalid.class).when(this.matchValidation).checkingCoachTotalTimeRequest(expectedRequest);

        assertThrows(DateTimeInvalid.class, () -> this.matchService.getTotalMatchByCoach(expectedRequest));
    }
}
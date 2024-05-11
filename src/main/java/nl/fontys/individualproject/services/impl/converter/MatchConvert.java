package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.Match;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.MatchEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;

import java.util.List;

public class MatchConvert {
    private MatchConvert(){}
    public static Match convert(MatchEntity matchEntity){
        StadiumEntity stadiumEntity = matchEntity.getStadium();
        Stadium stadium = Stadium.builder()
                .name(stadiumEntity.getName())
                .id(stadiumEntity.getId())
                .address(stadiumEntity.getAddress())
                .capacity(stadiumEntity.getCapacity())
                .surfaceType(stadiumEntity.getSurfaceType())
                .playingLocation(stadiumEntity.getPlayingLocation())
                .build();

        CoachEntity coachEntity = matchEntity.getCoach();
        Coach coach = Coach.builder()
                .id(coachEntity.getId())
                .username(coachEntity.getUsername())
                .gmail(coachEntity.getGmail())
                .dateOfBirth(coachEntity.getDateOfBirth())
                .playingLocation(coachEntity.getPlayingLocation())
                .experience(coachEntity.getExperience())
                .description(coachEntity.getDescription())
                .build();

        List<PlayerEntity> playerEntityList = matchEntity.getPlayers();
        List<Player> players = playerEntityList
                .stream()
                .map(player -> Player.builder()
                        .id(player.getId())
                        .username(player.getUsername())
                        .gmail(player.getGmail())
                        .dateOfBirth(player.getDateOfBirth())
                        .position(player.getPosition())
                        .playingLocation(player.getPlayingLocation()).build())
                .toList();

        return Match.builder()
                .id(matchEntity.getId())
                .stadium(stadium)
                .coach(coach)
                .time(matchEntity.getTime())
                .time_end(matchEntity.getTime_end())
                .date(matchEntity.getDate())
                .players(players)
                .build();
    }
}

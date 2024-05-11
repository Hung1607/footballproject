package nl.fontys.individualproject.services.validation.match;

import lombok.AllArgsConstructor;
import lombok.Generated;
import nl.fontys.individualproject.domain.request.CoachTotalMatchRequest;
import nl.fontys.individualproject.domain.request.MatchRequest;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.repositories.Entity.MatchEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.services.exception.DateTimeInvalid;
import nl.fontys.individualproject.services.exception.IdNotfoundException;
import nl.fontys.individualproject.services.exception.PlayerAlreadyJoined;
import nl.fontys.individualproject.services.exception.match.MatchWithFullPlayers;
import nl.fontys.individualproject.services.exception.match.OutTimeIsOver;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
@Generated
public class MatchValidation {
    public void checkingCoachTotalTimeRequest(CoachTotalMatchRequest coachTotalMatchRequest){
        LocalDate requestTimeStart = coachTotalMatchRequest.getDate_start();
        LocalDate requestTimeEnd = coachTotalMatchRequest.getDate_end();
        if(requestTimeStart.isAfter(requestTimeEnd)){
            throw new DateTimeInvalid();
        }

    }

    public void checkingTimeRequest(MatchRequest matchRequest, List<MatchEntity> createdMatchEntities){
        LocalTime requestTimeStart = matchRequest.getTime();
        LocalTime requestTimeEnd = matchRequest.getTime_end();

        if(requestTimeStart.isAfter(requestTimeEnd)){
            throw new DateTimeInvalid();
        }

        for(MatchEntity m : createdMatchEntities){
            if(m.getStadium().getId() == matchRequest.getStadium().getId()){
                LocalTime time_Start_same_stadium = m.getTime();
                LocalTime time_End_same_stadium = m.getTime_end();
                if(matchRequest.getDate().isEqual(m.getDate())){
                    if(requestTimeStart.equals(time_Start_same_stadium) || requestTimeEnd.equals(time_End_same_stadium)){
                        throw new DateTimeInvalid();
                    }
                    if((requestTimeStart.isAfter(time_Start_same_stadium) && requestTimeStart.isBefore(time_End_same_stadium) ) ||
                       (requestTimeEnd.isAfter(time_Start_same_stadium) && requestTimeEnd.isBefore(time_End_same_stadium)) ){
                        throw new DateTimeInvalid();
                    }
                }
            }
        }
    }

    public void checkingDuplicatePlayer(Player player, MatchEntity matchEntity){
        List<PlayerEntity> playerEntityList = matchEntity.getPlayers();
        for(PlayerEntity p : playerEntityList){
            if(player.getId() == p.getId()){
                throw new PlayerAlreadyJoined();
            }
        }
    }

    public boolean checkingPlayerExistInMatch(Player player, MatchEntity matchEntity){
        List<PlayerEntity> playerEntityList = matchEntity.getPlayers();
        for(PlayerEntity p : playerEntityList){
            if(player.getId() == p.getId()){
                return true;
            }
        }
        throw new IdNotfoundException();
    }

    public void checkingTimeAllowedToExit(MatchEntity matchEntity){
        LocalDate matchDate = matchEntity.getDate();
        LocalTime matchTime = matchEntity.getTime();

        LocalDateTime matchDateTime = LocalDateTime.of(matchDate, matchTime);
        LocalDateTime nowDateTime = LocalDateTime.now();

        if (nowDateTime.isAfter(matchDateTime)) {
            throw new DateTimeInvalid();
        }

        Duration durationUntilMatch = Duration.between(nowDateTime, matchDateTime);

        if (durationUntilMatch.toHours() < 8) {
            throw new OutTimeIsOver();
        }

    }


    public void checkingStadiumCapacity(MatchEntity matchEntity){
        int matchCapacity = matchEntity.getStadium().getCapacity();
        int playerSize = matchEntity.getPlayers().size();
        if(playerSize == matchCapacity){
            throw new MatchWithFullPlayers();
        }
    }
}

package nl.fontys.individualproject.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.individualproject.domain.request.CoachTotalMatchRequest;
import nl.fontys.individualproject.domain.request.MatchRequest;
import nl.fontys.individualproject.domain.response.*;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import nl.fontys.individualproject.repositories.Entity.MatchEntity;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import nl.fontys.individualproject.repositories.MatchRepository;
import nl.fontys.individualproject.services.exception.MatchIDNotFound;
import nl.fontys.individualproject.services.impl.converter.MatchConvert;
import nl.fontys.individualproject.services.validation.match.MatchValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class MatchService {
    private MatchRepository matchRepository;
    private MatchValidation matchValidation;

    @Transactional
    public void createMatch(MatchRequest matchRequest){
        List<MatchEntity> matchEntities = this.matchRepository.getMatchEntitiesByDate(matchRequest.getDate());

        matchValidation.checkingTimeRequest(matchRequest,matchEntities);

        Stadium stadium = matchRequest.getStadium();
        StadiumEntity stadiumEntity = StadiumEntity.builder()
                .name(stadium.getName())
                .id(stadium.getId())
                .address(stadium.getAddress())
                .capacity(stadium.getCapacity())
                .surfaceType(stadium.getSurfaceType())
                .playingLocation(stadium.getPlayingLocation())
                .build();

        Coach coach = matchRequest.getCoach();
        CoachEntity coachEntity = CoachEntity.builder()
                .id(coach.getId())
                .gmail(coach.getGmail())
                .dateOfBirth(coach.getDateOfBirth())
                .playingLocation(coach.getPlayingLocation())
                .experience(coach.getExperience())
                .description(coach.getDescription())
                .build();

        this.matchRepository.save(MatchEntity.builder()
                        .stadium(stadiumEntity)
                        .coach(coachEntity)
                        .date(matchRequest.getDate())
                        .time(matchRequest.getTime())
                        .time_end(matchRequest.getTime_end())
                        .build());
    }

    @Transactional
    public void addPlayerList(int matchId,Player player){
        Optional<MatchEntity> matchEntity = this.matchRepository.findById(matchId);

        if(matchEntity.isPresent()) {
            this.matchValidation.checkingStadiumCapacity(matchEntity.get());
            this.matchValidation.checkingDuplicatePlayer(player, matchEntity.get());

            List<PlayerEntity> playerEntityList = matchEntity.get().getPlayers();
            playerEntityList.add(PlayerEntity.builder()
                    .id(player.getId())
                    .username(player.getUsername())
                    .gmail(player.getGmail())
                    .dateOfBirth(player.getDateOfBirth())
                    .position(player.getPosition())
                    .playingLocation(player.getPlayingLocation()).build());
            this.matchRepository.save(matchEntity.get());
        }
        else{
            throw new MatchIDNotFound();
        }
    }

    @Transactional
    public void removePlayer(int matchId,Player player){

        Optional<MatchEntity> matchEntity = this.matchRepository.findById(matchId);

        if(matchEntity.isPresent()) {
            this.matchValidation.checkingTimeAllowedToExit(matchEntity.get());
            this.matchValidation.checkingDuplicatePlayer(player, matchEntity.get());
            boolean result = this.matchValidation.checkingPlayerExistInMatch(player,matchEntity.get());
            List<PlayerEntity> playerEntityList = matchEntity.get().getPlayers();
            if(result){
                playerEntityList.removeIf(p -> p.getId() == player.getId());
            }
            this.matchRepository.save(matchEntity.get());
        }
        else{
            throw new MatchIDNotFound();
        }
    }

    @Transactional
    public List<Match> getAllMatch(){
        List<MatchEntity> matchEntityList = this.matchRepository.findAll();
        return matchEntityList.stream()
                .map(MatchConvert::convert)
                .toList();
    }

    @Transactional
    public Optional<Match> getMatchByID(int id){
        Optional<MatchEntity> optionalMatchEntity = this.matchRepository.findById(id);
        if(optionalMatchEntity.isPresent()){
            MatchEntity m = optionalMatchEntity.get();
            return Optional.of(m).map(MatchConvert::convert);
        }
        throw new MatchIDNotFound();
    }

    @Transactional
    public List<Object[]> getTotalMatchByCoach(CoachTotalMatchRequest coachTotalMatchRequest){

        this.matchValidation.checkingCoachTotalTimeRequest(coachTotalMatchRequest);

        return this.matchRepository.getTotalMatchByAllCoach(coachTotalMatchRequest.getDate_start(),
                                                                coachTotalMatchRequest.getDate_end());
    }
}

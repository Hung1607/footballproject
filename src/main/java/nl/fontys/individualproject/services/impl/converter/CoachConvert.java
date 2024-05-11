package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;

public class CoachConvert {
    private CoachConvert(){}
    public static Coach convert(CoachEntity coachEntity){
        return Coach.builder()
                .id(coachEntity.getId())
                .gmail(coachEntity.getGmail())
                .username(coachEntity.getUsername())
                .dateOfBirth(coachEntity.getDateOfBirth())
                .playingLocation(coachEntity.getPlayingLocation())
                .experience(coachEntity.getExperience())
                .description(coachEntity.getDescription())
                .status(coachEntity.getStatus())
                .build();
    }
}

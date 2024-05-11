package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;

public class StadiumConvert {
    private StadiumConvert(){}
    public static Stadium convert(StadiumEntity stadiumEntity){
        return Stadium.builder()
                .id(stadiumEntity.getId())
                .name(stadiumEntity.getName())
                .playingLocation((stadiumEntity.getPlayingLocation()))
                .address((stadiumEntity.getAddress()))
                .capacity((stadiumEntity.getCapacity()))
                .surfaceType(stadiumEntity.getSurfaceType())
                .build();
    }
}

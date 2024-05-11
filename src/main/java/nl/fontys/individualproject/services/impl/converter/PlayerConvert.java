package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;

public class PlayerConvert {
    private PlayerConvert(){}
    public static Player convert(PlayerEntity playerEntity){
        return Player.builder()
                .id(playerEntity.getId())
                .gmail(playerEntity.getGmail())
                .username(playerEntity.getUsername())
                .dateOfBirth(playerEntity.getDateOfBirth())
                .playingLocation(playerEntity.getPlayingLocation())
                .position(playerEntity.getPosition())
                .build();
    }
}

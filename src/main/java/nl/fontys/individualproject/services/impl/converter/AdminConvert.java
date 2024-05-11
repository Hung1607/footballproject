package nl.fontys.individualproject.services.impl.converter;

import nl.fontys.individualproject.domain.response.Admin;
import nl.fontys.individualproject.repositories.Entity.AdminEntity;

public class AdminConvert {
    private AdminConvert(){}
    public static Admin convert(AdminEntity adminEntity){
        return Admin.builder()
                .id(adminEntity.getId())
                .gmail(adminEntity.getGmail())
                .username(adminEntity.getUsername())
                .dateOfBirth(adminEntity.getDateOfBirth())
                .playingLocation(adminEntity.getPlayingLocation())
                .build();
    }
}

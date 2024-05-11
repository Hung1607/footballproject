package nl.fontys.individualproject.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.domain.request.StadiumRequest;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import nl.fontys.individualproject.repositories.StadiumRepository;
import nl.fontys.individualproject.services.StadiumCase;
import nl.fontys.individualproject.services.impl.converter.StadiumConvert;
import nl.fontys.individualproject.services.validation.stadium.StadiumValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StadiumService implements StadiumCase{
    private StadiumRepository stadiumRepository;
    private StadiumValidation stadiumValidation;

    @Transactional
    public void createStadium(StadiumRequest stadiumRequest){
        this.stadiumValidation.stadiumRequestChecking(stadiumRequest);

        this.stadiumRepository.save(StadiumEntity.builder()
                        .name(stadiumRequest.getName())
                        .address(stadiumRequest.getAddress())
                        .playingLocation(stadiumRequest.getPlayingLocation())
                        .capacity(stadiumRequest.getCapacity())
                        .surfaceType(stadiumRequest.getSurfaceType())
                        .build());
    }

    public Optional<Stadium> getStadiumByID(int id){
        Optional<StadiumEntity> stadiumEntity = this.stadiumRepository.findById(id);

        this.stadiumValidation.getStadiumByIdChecking(stadiumEntity);

        return stadiumEntity.map(StadiumConvert::convert);
    }

    public List<Stadium> getAllStadium(){
        return this.stadiumRepository.findAll().stream()
                .map(StadiumConvert::convert)
                .toList();
    }

    @Transactional
    public void updateStadium(Stadium updatedStadium){
        this.stadiumValidation.updateStadiumChecking(updatedStadium);

        this.stadiumRepository
                .save(StadiumEntity.builder()
                        .id(updatedStadium.getId())
                        .name(updatedStadium.getName())
                        .address(updatedStadium.getAddress())
                        .playingLocation(updatedStadium.getPlayingLocation())
                        .capacity(updatedStadium.getCapacity())
                        .surfaceType(updatedStadium.getSurfaceType())
                        .build());
    }

    public void deleteStadiumByID(int id){
        this.stadiumValidation.idChecking(id);
        this.stadiumRepository.deleteById(id);
    }
}

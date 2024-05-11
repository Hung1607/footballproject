package nl.fontys.individualproject.services.validation.stadium;

import lombok.AllArgsConstructor;
import lombok.Generated;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.domain.request.StadiumRequest;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import nl.fontys.individualproject.repositories.StadiumRepository;
import nl.fontys.individualproject.services.exception.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class StadiumValidation {

    private StadiumRepository stadiumRepository;

    public void stadiumRequestChecking(StadiumRequest stadiumRequest){
        if(stadiumRequest.getName().isBlank() || stadiumRequest.getAddress().isBlank() ||
               stadiumRequest.getCapacity() <= 0 ){
            throw new BlankInput();
        }

        if(this.stadiumRepository.existsByName(stadiumRequest.getName())){
            throw new StadiumNameAlreadyExist();
        }

        if(this.stadiumRepository.existsByAddress(stadiumRequest.getAddress())){
            throw new StadiumAddressAlreadyExist();
        }
    }

    public void getStadiumByIdChecking(Optional<StadiumEntity> stadiumEntityOptional){
        if(stadiumEntityOptional.isEmpty()){
            throw new IdNotfoundException();
        }
    }

    public void updateStadiumChecking(Stadium updatedStadium){
        if(updatedStadium.getName().isBlank() || updatedStadium.getAddress().isBlank() ||
                updatedStadium.getCapacity() <= 0 ) {
            throw new BlankInput();
        }

        List<StadiumEntity> duplicateList = this.stadiumRepository.getByNameAndAddressIgnoreIdCase(updatedStadium.getName()
                ,updatedStadium.getAddress()
                ,updatedStadium.getId());
        if(!duplicateList.isEmpty()){
            throw new CredentialAlreadyExist();
        }
    }

    public void idChecking(int id){
        if(!this.stadiumRepository.existsById(id)) {
            throw new IdNotfoundException();
        }
    }


}

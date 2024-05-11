package nl.fontys.individualproject.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Generated;
import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.domain.request.StadiumRequest;
import nl.fontys.individualproject.services.StadiumCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Generated
@AllArgsConstructor
@RequestMapping("/stadium")
public class StadiumController {

    private StadiumCase stadiumCase;

    @GetMapping
    public ResponseEntity<List<Stadium>> getAllStadium(){
        try{
            return ResponseEntity.ok(this.stadiumCase.getAllStadium());
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    @RolesAllowed("Admin")
    public ResponseEntity<Stadium> getStadiumByID(@PathVariable ("id") int id){
        Optional<Stadium> stadiumOptional = this.stadiumCase.getStadiumByID(id);
        if(stadiumOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stadiumOptional.get());
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<Void> createStadium(@RequestBody @Valid StadiumRequest stadiumRequest){
        this.stadiumCase.createStadium(stadiumRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    @RolesAllowed("Admin")
    public ResponseEntity<Void> updateStadium(@PathVariable ("id") int id,@RequestBody @Valid Stadium stadium){
        stadium.setId(id);
        this.stadiumCase.updateStadium(stadium);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @RolesAllowed("Admin")
    public ResponseEntity<Void> deleteStadium(@PathVariable ("id") int id){
        this.stadiumCase.deleteStadiumByID(id);
        return ResponseEntity.noContent().build();
    }

}

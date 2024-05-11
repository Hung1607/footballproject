package nl.fontys.individualproject.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Generated;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.User;
import nl.fontys.individualproject.domain.request.CoachRequest;
import nl.fontys.individualproject.services.CoachCreate;
import nl.fontys.individualproject.services.UserFunctionality;
import nl.fontys.individualproject.services.impl.CoachStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Generated
@AllArgsConstructor
@RequestMapping("/coach")
public class CoachController {

    private UserFunctionality userFunctionality;
    private CoachCreate coachCreate;

    private CoachStatusService coachStatusService;

    @GetMapping
//    @RolesAllowed({"Admin"})
    public ResponseEntity<List<User>> getAllAvailableCoach(){
        try{
            return ResponseEntity.ok(this.userFunctionality.getAllUser(Coach.class));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getCoach")
    @RolesAllowed({"Coach"})
    public ResponseEntity<User> getCoachByID(@RequestParam int id) {
        Optional<User> coachOptinal = this.userFunctionality.getUserByID(id,Coach.class);
        if(coachOptinal.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coachOptinal.get());
    }

    @PostMapping
    public ResponseEntity<Void> createCoach(@RequestBody @Valid CoachRequest coachRequest){
        this.coachCreate.createCoach(coachRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping()
    @RolesAllowed({"Coach"})
    public ResponseEntity<Void> updatePlayer(@RequestParam int id, @Valid @RequestBody Coach coach){
        coach.setId(id);
        this.userFunctionality.updateUser(coach);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    @RolesAllowed({"Coach"})
    public ResponseEntity<Void> deleteCoach(@RequestParam int id){
        this.userFunctionality.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

// Coach Status
    @GetMapping("/coachesRequest")
    @RolesAllowed({"Admin"})
    public ResponseEntity<List<Coach>> getAllCoachRequest() {
        List<Coach> coach = this.coachStatusService.getAllCoachRequest();
        return ResponseEntity.ok(coach);
    }

    @GetMapping("/coachesActive")
    @RolesAllowed({"Admin"})
    public ResponseEntity<List<Coach>> getAllActiveCoach() {
        List<Coach> coach = this.coachStatusService.getAllActiveCoach();
        return ResponseEntity.ok(coach);
    }

    @PutMapping("/acceptStatus/{id}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<Void> acceptCoachStatus(@PathVariable("id") int id){
        this.coachStatusService.acceptCoachStatus(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/declineStatus/{id}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<Void> declineCoachStatus(@PathVariable("id") int id){
        this.coachStatusService.declineCoachStatus(id);
        return ResponseEntity.noContent().build();
    }

}

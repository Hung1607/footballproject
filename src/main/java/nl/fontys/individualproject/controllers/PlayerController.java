package nl.fontys.individualproject.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.domain.response.User;
import nl.fontys.individualproject.domain.request.PlayerRequest;
import nl.fontys.individualproject.services.PlayerCreate;
import nl.fontys.individualproject.services.UserFunctionality;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private PlayerCreate playerCreate;
    private UserFunctionality userFunctionality;

    @GetMapping
    @RolesAllowed({"Admin"})
    public ResponseEntity<List<User>> getAllAvailablePlayer(){
        try{
            return ResponseEntity.ok(this.userFunctionality.getAllUser(Player.class));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getPlayer")
    @RolesAllowed({"Player"})
    public ResponseEntity<User> getPlayerByID(@RequestParam int id) {
        Optional<User> playerOptional = this.userFunctionality.getUserByID(id,Player.class);
        if(playerOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playerOptional.get());
    }

    @PostMapping
    public ResponseEntity<Void> createPlayer(@RequestBody @Valid PlayerRequest playerRequest){
         this.playerCreate.createPlayer(playerRequest);
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping()
    @RolesAllowed({"Player"})
    public ResponseEntity<Void> updatePlayer(@RequestParam int id,@Valid @RequestBody Player player){
        try{
            player.setId(id);
            this.userFunctionality.updateUser(player);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping()
    @RolesAllowed({"Player"})
    public ResponseEntity<Void> deletePlayer(@RequestParam int id){
        this.userFunctionality.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}

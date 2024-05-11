package nl.fontys.individualproject.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.individualproject.domain.request.AdminRequest;
import nl.fontys.individualproject.domain.response.Admin;
import nl.fontys.individualproject.domain.response.User;
import nl.fontys.individualproject.services.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> createAdmin(@RequestBody @Valid AdminRequest adminRequest){
        this.userService.createAdmin(adminRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<Void> deleteAdminByID(@PathVariable  ("id") int id){
        this.userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<Void> updateAdminByID(@PathVariable("id") int id, @Valid @RequestBody Admin admin){
        admin.setId(id);
        this.userService.updateUser(admin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<User> getAdminByID(@PathVariable("id") int id) {
        Optional<User> playerOptional = this.userService.getUserByID(id,Admin.class);
        if(playerOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playerOptional.get());
    }

}

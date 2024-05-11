package nl.fontys.individualproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Generated;
import nl.fontys.individualproject.domain.request.login.UserLogin;
import nl.fontys.individualproject.domain.response.LoginResponse;
import nl.fontys.individualproject.services.impl.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Generated
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserLogin userLogin){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.loginService.getLogin(userLogin.getUsername(), userLogin.getPassword()));
    }

}

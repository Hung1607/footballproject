package nl.fontys.individualproject.services.validation.login;

import nl.fontys.individualproject.repositories.UserRepository;
import nl.fontys.individualproject.services.exception.BlankInput;
import nl.fontys.individualproject.services.exception.UsernameNotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginValidationTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private LoginValidation loginValidation;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkingUsername() {
        when(this.userRepository.existsByUsername(anyString())).thenReturn(true);
        this.loginValidation.checkingUsername("Asd");
    }

    @Test
    void checkingUsername_ShouldThrowUsernameNotFound() {
        assertThrows(UsernameNotFound.class, () -> this.loginValidation.checkingUsername("asss"));
    }

}
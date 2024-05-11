package nl.fontys.individualproject.configuration.security.auth;

import nl.fontys.individualproject.configuration.security.token.AccessToken;
import nl.fontys.individualproject.services.exception.UnauthorizedDataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthorizationTest {

    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private UserAuthorization userAuthorization;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void authorize() {
        when(this.accessToken.getUserId()).thenReturn(12);
        this.userAuthorization.authorize(12);
    }

    @Test
    void authorize_throwException() {
        when(this.accessToken.getUserId()).thenReturn(123);
        assertThrows(UnauthorizedDataAccessException.class, () -> this.userAuthorization.authorize(12));
    }

}
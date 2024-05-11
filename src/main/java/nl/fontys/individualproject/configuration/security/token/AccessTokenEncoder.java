package nl.fontys.individualproject.configuration.security.token;


public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}

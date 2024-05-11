package nl.fontys.individualproject.configuration.security.token;

public interface  AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}

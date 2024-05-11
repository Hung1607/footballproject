package nl.fontys.individualproject.configuration.security.token;


import java.util.Set;

public interface AccessToken {
    String getUsername();
    int getUserId();
    Set<String> getRoles();

    boolean hasRole(String classType);

}

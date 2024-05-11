package nl.fontys.individualproject.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.individualproject.domain.enume.PlayingLocation;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AdminRequest {
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private String gmail;
    private PlayingLocation workingLocation;
}
